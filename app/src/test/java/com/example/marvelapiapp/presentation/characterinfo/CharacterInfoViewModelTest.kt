package com.example.marvelapiapp.presentation.characterinfo

import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.response.UseCaseResponse
import com.example.domain.usecase.character.info.GetCharacterInfoUseCaseImpl
import com.example.marvelapiapp.base.BaseTest
import com.example.marvelapiapp.databinding.setError
import com.example.marvelapiapp.databinding.setLoading
import com.example.marvelapiapp.databinding.setSuccess
import com.example.marvelapiapp.presentation.characterInfo.CharacterInfoViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class CharacterInfoViewModelTest : BaseTest() {
    private lateinit var viewModel: CharacterInfoViewModel
    private val useCase = mockk<GetCharacterInfoUseCaseImpl>(relaxed = true)
    private val flowMock = mockk<MutableStateFlow<UseCaseResponse<CharacterInfo>>>(relaxed = true)

    override fun testInit() {
        viewModel = spyk(CharacterInfoViewModel(useCase), recordPrivateCalls = true)
    }

    @Test
    fun `test setCharacterData`() {
        val characterInfo = CharacterInfo(1, "Test", "",
            "", listOf(), listOf(), listOf(), listOf())
        viewModel.setCharacterData(characterInfo)
        val result = viewModel.characterData
        Assert.assertEquals(characterInfo.id, result.value?.id)
        Assert.assertEquals(characterInfo.name, result.value?.name)
    }

    @Test
    fun `test requestCharacterData`() = runTest {
        val response = mockk<UseCaseResponse<CharacterInfo?>>(relaxed = true)
        val characterInfo = mockk<CharacterInfo>(relaxed = true)
        every { response.data } returns characterInfo
        coEvery {
            useCase.execute(1, any(), any())
        } coAnswers {
            secondArg<(UseCaseResponse<CharacterInfo?>) -> Unit>().invoke(response)
        }
        viewModel.setTestValue("characterInfoFlow", flowMock)

        viewModel.requestCharacterData(1)

        verify(exactly = 1) { flowMock.setLoading() }
        launch(Dispatchers.Main) {
            coVerify(exactly = 1) { useCase.execute(1, any(), any()) }
            coVerify(exactly = 1) { viewModel.notifyLoadFinished() }
            coVerify(exactly = 1) { flowMock.setSuccess(characterInfo) }
        }
    }

    @Test
    fun `test requestCharacterData error`() = runTest {
        val throwable = mockk<Throwable>(relaxed = true)
        coEvery {
            useCase.execute(1, any(), any())
        } coAnswers {
            thirdArg<(Throwable) -> Unit>().invoke(throwable)
        }

        viewModel.setTestValue("characterInfoFlow", flowMock)
        viewModel.requestCharacterData(1)

        verify(exactly = 1) { flowMock.setLoading() }
        launch(Dispatchers.Main) {
            coVerify(exactly = 1) { useCase.execute(1, any(), any()) }
            coVerify(exactly = 1) { viewModel.notifyLoadFinished() }
            coVerify(exactly = 1) { flowMock.setError(throwable) }
        }
    }

    @Test
    fun `test getCharacterState`() {
        val privateState = viewModel.getTestValue<MutableStateFlow<UseCaseResponse<CharacterInfo>>>(
            "characterInfoFlow")
        val state = viewModel.getCharacterState()

        Assert.assertEquals(privateState.value.status, state.value.status)
        Assert.assertEquals(privateState.value.data, state.value.data)
    }

    @Test
    fun `test notifyLoadFinished`() {
        Assert.assertEquals(viewModel.isLoading.value, true)
        viewModel.notifyLoadFinished()
        Assert.assertEquals(viewModel.isLoading.value, false)
    }
}
