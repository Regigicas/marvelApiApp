package com.example.marvelapiapp.viewmodel.characters

import com.example.marvelapiapp.base.BaseTest
import com.example.marvelapiapp.constant.CharactersRequest
import com.example.marvelapiapp.navigation.NavigationManager
import com.example.marvelapiapp.repository.characters.CharactersRepository
import io.mockk.coVerify
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert
import org.junit.Test

class CharactersViewModelTest : BaseTest() {
    private lateinit var viewModel: CharactersViewModel
    private val repository = mockk<CharactersRepository>(relaxed = true)

    override fun testInit() {
        viewModel = spyk(CharactersViewModel(repository), recordPrivateCalls = true)
    }

    @Test
    fun `test collectCharacters`() {
        viewModel.collectCharacters()

        verify(exactly = 1) { viewModel["launchCollector"](true) }
    }

    @Test
    fun `test getCharacterState`() {
        val privateState = viewModel.getTestValue<MutableStateFlow<CharactersRequest>>(
            "charactersFlow")
        val state = viewModel.getCharactersState()

        Assert.assertEquals(privateState.value.type, state.value.type)
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
    fun `test handleServiceError`() {
        mockkObject(NavigationManager)
        justRun { NavigationManager.showErrorOverlay() }

        viewModel.handleServiceError(false)

        verify(exactly = 0) { NavigationManager.showErrorOverlay() }
        Assert.assertEquals(true, viewModel.errorVisible.value)
    }

    @Test
    fun `test handleServiceError has data`() {
        mockkObject(NavigationManager)
        justRun { NavigationManager.showErrorOverlay() }

        viewModel.handleServiceError(true)

        verify(exactly = 1) { NavigationManager.showErrorOverlay() }
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
    fun `test launchCollector`() {
        viewModel.setTestValue("currentOffset", 20)
        viewModel.callPrivateMethod<Unit>("launchCollector", false)

        coVerify(exactly = 1) { repository.runCharactersCollector(any(), 20, 20) }
        val offset = viewModel.getTestValue<Int>("currentOffset")
        Assert.assertEquals(40, offset)
    }

    @Test
    fun `test launchCollector refresh`() {
        viewModel.setTestValue("currentOffset", 20)
        viewModel.callPrivateMethod<Unit>("launchCollector", true)

        coVerify(exactly = 1) { repository.runCharactersCollector(any(), 20, 0) }
        val offset = viewModel.getTestValue<Int>("currentOffset")
        Assert.assertEquals(20, offset)
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
