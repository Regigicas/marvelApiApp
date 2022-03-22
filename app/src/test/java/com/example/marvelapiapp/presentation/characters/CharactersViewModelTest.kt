package com.example.marvelapiapp.presentation.characters

import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.response.UseCaseResponse
import com.example.domain.usecase.character.list.GetCharactersUseCase
import com.example.domain.usecase.character.list.GetCharactersUseCaseImpl
import com.example.marvelapiapp.base.BaseTest
import com.example.marvelapiapp.databinding.setError
import com.example.marvelapiapp.databinding.setFrom
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class CharactersViewModelTest : BaseTest() {
    private lateinit var viewModel: CharactersViewModel
    private val useCase = mockk<GetCharactersUseCaseImpl>(relaxed = true)
    private val flowMock =
        mockk<MutableStateFlow<UseCaseResponse<List<CharacterInfo>>>>(relaxed = true)

    override fun testInit() {
        viewModel = spyk(CharactersViewModel(useCase), recordPrivateCalls = true)
    }

    @Test
    fun `test collectCharacters`() {
        viewModel.collectCharacters()

        verify(exactly = 1) { viewModel["launchCollector"](true) }
    }

    @Test
    fun `test getCharacterState`() {
        val privateState = viewModel.getTestValue<StateFlow<UseCaseResponse<List<CharacterInfo>>>>(
            "charactersFlow")
        val state = viewModel.getCharactersState()

        Assert.assertEquals(privateState.value.status, state.value.status)
        Assert.assertEquals(privateState.value.data, state.value.data)
    }

    @Test
    fun `test loadMoreCharacters`() {
        viewModel.setTestValue("_isLoading", MutableStateFlow(false))
        viewModel.setTestValue("_isRefreshing", MutableStateFlow(false))
        viewModel.loadMoreCharacters()

        verify(exactly = 1) { viewModel["launchCollector"](false) }
    }

    @Test
    fun `test loadMoreCharacters is loading`() {
        viewModel.setTestValue("_isLoading", MutableStateFlow(true))
        viewModel.setTestValue("_isRefreshing", MutableStateFlow(false))
        viewModel.loadMoreCharacters()

        verify(exactly = 0) { viewModel["launchCollector"](false) }
    }

    @Test
    fun `test loadMoreCharacters is refreshing`() {
        viewModel.setTestValue("_isLoading", MutableStateFlow(false))
        viewModel.setTestValue("_isRefreshing", MutableStateFlow(true))
        viewModel.loadMoreCharacters()

        verify(exactly = 0) { viewModel["launchCollector"](false) }
    }

    @Test
    fun `test notifyServiceError`() {
        val privateState = viewModel.getTestValue<MutableStateFlow<Boolean>>(
            "_errorVisible").value
        viewModel.notifyServiceError()
        val state = viewModel.errorVisible

        Assert.assertEquals(false, privateState)
        Assert.assertEquals(true, state.value)
    }

    @Test
    fun `test refreshData`() {
        viewModel.refreshData()

        verify(exactly = 1) { viewModel["launchCollector"](true) }
    }

    @Test
    fun `test checkDataResetAndClear`() {
        viewModel.setTestValue("needsDataReset", true)
        val funcResult = viewModel.checkDataResetAndClear()
        val result = viewModel.getTestValue<Boolean>("needsDataReset")

        Assert.assertEquals(false, result)
        Assert.assertEquals(true, funcResult)
    }

    @Test
    fun `test launchCollector`() = runTest {
        val dto = GetCharactersUseCase.GetCharactersParams(20, 20)
        val response = mockk<UseCaseResponse<List<CharacterInfo>>>(relaxed = true)
        coEvery {
            useCase.execute(dto, any(), any())
        } coAnswers {
            secondArg<(UseCaseResponse<List<CharacterInfo>>) -> Unit>().invoke(response)
        }

        viewModel.setTestValue("currentOffset", 20)
        viewModel.setTestValue("charactersFlow", flowMock)

        viewModel.callPrivateMethod<Unit>("launchCollector", false)

        launch(Dispatchers.Main) {
            coVerify(exactly = 1) { useCase.execute(dto, any(), any()) }
            coVerify(exactly = 1) { viewModel.notifyLoadFinished() }
            coVerify(exactly = 1) { flowMock.setFrom(response) }
            val offset = viewModel.getTestValue<Int>("currentOffset")
            Assert.assertEquals(40, offset)
        }
    }

    @Test
    fun `test launchCollector refresh`() = runTest {
        val dto = GetCharactersUseCase.GetCharactersParams(20, 0)
        val throwable = mockk<Throwable>(relaxed = true)
        coEvery {
            useCase.execute(dto, any(), any())
        } coAnswers {
            thirdArg<(Throwable) -> Unit>().invoke(throwable)
        }

        viewModel.setTestValue("currentOffset", 20)
        viewModel.setTestValue("charactersFlow", flowMock)

        viewModel.callPrivateMethod<Unit>("launchCollector", true)

        launch(Dispatchers.Main) {
            coVerify(exactly = 1) { useCase.execute(dto, any(), any()) }
            coVerify(exactly = 1) { viewModel.notifyLoadFinished() }
            coVerify(exactly = 1) { flowMock.setError(throwable) }
            val offset = viewModel.getTestValue<Int>("currentOffset")
            Assert.assertEquals(0, offset)
        }
    }

    @Test
    fun `test notifyLoadFinished`() {
        Assert.assertEquals(viewModel.isLoading.value, true)
        viewModel.notifyLoadFinished()
        Assert.assertEquals(viewModel.isLoading.value, false)
    }

    @Test
    fun `test isRefreshing`() {
        val privateState = viewModel.getTestValue<MutableStateFlow<Boolean>>(
            "_isRefreshing")
        val state = viewModel.isRefreshing

        Assert.assertEquals(privateState.value, state.value)
    }
}
