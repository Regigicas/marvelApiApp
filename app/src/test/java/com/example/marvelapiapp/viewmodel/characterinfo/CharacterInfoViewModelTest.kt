package com.example.marvelapiapp.viewmodel.characterinfo

import com.example.common.characters.CharacterInfo
import com.example.marvelapiapp.base.BaseTest
import com.example.marvelapiapp.constant.CharacterInfoRequest
import com.example.marvelapiapp.repository.base.ResponseType
import com.example.marvelapiapp.repository.characters.CharactersRepository
import com.example.marvelapiapp.viewmodel.characterInfo.CharacterInfoViewModel
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert
import org.junit.Test

class CharacterInfoViewModelTest : BaseTest() {
    private lateinit var viewModel: CharacterInfoViewModel
    private val characterRepository = mockk<CharactersRepository>(relaxed = true)

    override fun testInit() {
        viewModel = spyk(CharacterInfoViewModel(characterRepository), recordPrivateCalls = true)
    }

    @Test
    fun `test setCharacterData`() {
        val characterInfo = CharacterInfo(1, "Test", null,
            null, null, null, null, null)
        viewModel.setCharacterData(characterInfo)
        val result = viewModel.characterData
        Assert.assertEquals(characterInfo.id, result.value?.id)
        Assert.assertEquals(characterInfo.name, result.value?.name)
    }

    @Test
    fun `test requestCharacterData`() {
        viewModel.requestCharacterData(1)

        val state = viewModel.getTestValue<MutableStateFlow<CharacterInfoRequest>>(
            "characterInfoFlow")

        Assert.assertEquals(ResponseType.LOADING, state.value.type)
        coVerify(exactly = 1) { characterRepository.runCharacterCollector(any(), 1) }
    }

    @Test
    fun `test getCharacterState`() {
        val privateState = viewModel.getTestValue<MutableStateFlow<CharacterInfoRequest>>(
            "characterInfoFlow")
        val state = viewModel.getCharacterState()

        Assert.assertEquals(privateState.value.type, state.value.type)
        Assert.assertEquals(privateState.value.data, state.value.data)
    }

    @Test
    fun `test notifyLoadFinished`() {
        Assert.assertEquals(viewModel.isLoading.value, true)
        viewModel.notifyLoadFinished()
        Assert.assertEquals(viewModel.isLoading.value, false)
    }
}
