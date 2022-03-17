package com.example.marvelapiapp.repository.characters

import com.example.common.characters.CharacterData
import com.example.common.characters.CharacterResponse
import com.example.database.dao.CharacterDao
import com.example.marvelapiapp.base.BaseTest
import com.example.marvelapiapp.constant.CharacterInfoRequest
import com.example.marvelapiapp.constant.CharactersRequest
import com.example.marvelapiapp.repository.base.ResponseType
import com.example.network.characters.CharacterServiceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CharactersRepositoryTest : BaseTest() {
    private lateinit var repository: CharactersRepository
    private val service = mockk<CharacterServiceImpl>(relaxed = true)
    private val dao = mockk<CharacterDao>(relaxed = true)

    override fun testInit() {
        repository = spyk(CharactersRepository(service, dao), recordPrivateCalls = true)
    }

    @Test
    fun `test runCharactersCollector`() {
        val serviceReturn = mockk<CharacterResponse>(relaxed = true)
        coEvery { dao.getAll(20, 0) } returns listOf()
        coEvery {
            service.getCharacters(any(), any(), any(), 20, 0)
        } returns serviceReturn

        val state = MutableStateFlow(
            CharactersRequest(ResponseType.NOT_STARTED, null, null))
        runBlocking { repository.runCharactersCollector(state, 20, 0) }

        verify(exactly = 1) { repository.getAuthParams() }
        coVerify(exactly = 1) { dao.getAll(20, 0) }
        coVerify(exactly = 1) { service.getCharacters(any(), any(), any(), 20, 0) }
        coVerify(exactly = 1) { dao.insertAll(any()) }
    }

    @Test
    fun `test runCharactersCollector has cache`() {
        coEvery { dao.getAll(20, 0) } returns listOf(mockk(relaxed = true))

        val state = MutableStateFlow(
            CharactersRequest(ResponseType.NOT_STARTED, null, null))
        runBlocking { repository.runCharactersCollector(state, 20, 0) }

        verify(exactly = 1) { repository.getAuthParams() }
        coVerify(exactly = 1) { dao.getAll(20, 0) }
        coVerify(exactly = 0) { service.getCharacters(any(), any(), any(), 20, 0) }
    }

    @Test
    fun `test runCharacterCollector`() {
        coEvery { dao.getId(1) } returns null
        coEvery {
            service.getCharacter(1, any(), any(), any())
        } returns CharacterResponse(1, "", "",
            CharacterData(0, 0, 0, 0,
                listOf(mockk(relaxed = true))))

        val state = MutableStateFlow(
            CharacterInfoRequest(ResponseType.NOT_STARTED, null, null))
        runBlocking { repository.runCharacterCollector(state, 1) }

        verify(exactly = 1) { repository.getAuthParams() }
        coVerify(exactly = 1) { dao.getId(1) }
        coVerify(exactly = 1) { service.getCharacter(1, any(), any(), any()) }
        coVerify(exactly = 1) { dao.insert(any()) }
    }

    @Test
    fun `test runCharacterCollector has cache`() {
        coEvery { dao.getId(1) } returns mockk(relaxed = true)

        val state = MutableStateFlow(
            CharacterInfoRequest(ResponseType.NOT_STARTED, null, null))
        runBlocking { repository.runCharacterCollector(state, 1) }

        verify(exactly = 1) { repository.getAuthParams() }
        coVerify(exactly = 1) { dao.getId(1) }
        coVerify(exactly = 0) { service.getCharacter(1, any(), any(), any()) }
    }
}
