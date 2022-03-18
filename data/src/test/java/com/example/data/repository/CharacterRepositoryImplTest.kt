package com.example.data.repository

import com.example.data.base.BaseTest
import com.example.data.datasource.database.dao.CharacterDao
import com.example.data.datasource.database.model.character.CharacterInfoCache
import com.example.data.datasource.network.manager.CharacterApi
import com.example.data.datasource.network.model.character.CharacterInfoNet
import com.example.data.datasource.network.model.character.CharacterResponseNet
import com.example.data.repository.character.CharacterRepositoryImpl
import com.example.domain.mapper.EntityMapper
import com.example.domain.model.character.CharacterInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CharacterRepositoryImplTest : BaseTest() {
    private lateinit var repository: CharacterRepositoryImpl
    private val service = mockk<CharacterApi>(relaxed = true)
    private val dao = mockk<CharacterDao>(relaxed = true)
    private val dbMapper = mockk<EntityMapper<CharacterInfoCache, CharacterInfo>>(relaxed = true)
    private val networkMapper = mockk<EntityMapper<CharacterInfoNet, CharacterInfo>>(relaxed = true)

    override fun testInit() {
        repository = spyk(CharacterRepositoryImpl(service, dao, dbMapper, networkMapper))
    }

    @Test
    fun `test getCharacters`() {
        val serviceReturn = mockk<CharacterResponseNet>(relaxed = true)
        every { serviceReturn.data?.results } returns listOf(mockk(relaxed = true))
        coEvery { dao.getAll(20, 0) } returns listOf()
        coEvery {
            service.getCharacters(20, 0)
        } returns serviceReturn

        runBlocking { repository.getCharacters(20, 0) }

        coVerify(exactly = 1) { dao.getAll(20, 0) }
        coVerify(exactly = 1) { service.getCharacters(20, 0) }
        coVerify(exactly = 1) { dao.insertAll(any()) }
    }

    @Test
    fun `test getCharacters result empty`() {
        val serviceReturn = mockk<CharacterResponseNet>(relaxed = true)
        coEvery { dao.getAll(20, 0) } returns listOf()
        coEvery {
            service.getCharacters(20, 0)
        } returns serviceReturn

        runBlocking { repository.getCharacters(20, 0) }

        coVerify(exactly = 1) { dao.getAll(20, 0) }
        coVerify(exactly = 1) { service.getCharacters(20, 0) }
        coVerify(exactly = 0) { dao.insertAll(any()) }
    }

    @Test
    fun `test getCharacters has cache`() {
        coEvery { dao.getAll(20, 0) } returns listOf(mockk(relaxed = true))

        runBlocking { repository.getCharacters(20, 0) }

        coVerify(exactly = 1) { dao.getAll(20, 0) }
        coVerify(exactly = 0) { service.getCharacters(20, 0) }
    }

    @Test
    fun `test getCharacter`() {
        coEvery { dao.getId(1) } returns null
        coEvery { service.getCharacter(1) } returns mockk(relaxed = true)
        coEvery {
            service.getCharacter(1)
        } returns mockk(relaxed = true)

        runBlocking { repository.getCharacter(1) }

        coVerify(exactly = 1) { dao.getId(1) }
        coVerify(exactly = 1) { service.getCharacter(1) }
    }

    @Test
    fun `test getCharacter has cache`() {
        coEvery { dao.getId(1) } returns mockk(relaxed = true)
        coEvery {
            service.getCharacter(1)
        } returns mockk(relaxed = true)

        runBlocking { repository.getCharacter(1) }

        coVerify(exactly = 1) { dao.getId(1) }
        coVerify(exactly = 0) { service.getCharacter(1) }
    }
}
