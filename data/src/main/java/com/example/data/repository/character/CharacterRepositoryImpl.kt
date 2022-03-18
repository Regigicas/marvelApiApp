package com.example.data.repository.character

import com.example.data.datasource.database.dao.CharacterDao
import com.example.data.datasource.database.model.character.CharacterInfoCache
import com.example.data.datasource.network.manager.CharacterApi
import com.example.data.datasource.network.model.character.CharacterInfoNet
import com.example.domain.mapper.EntityMapper
import com.example.domain.model.character.CharacterInfo
import com.example.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val networkApi: CharacterApi,
    private val dao: CharacterDao,
    private val databaseMapper: EntityMapper<CharacterInfoCache, CharacterInfo>,
    private val networkMapper: EntityMapper<CharacterInfoNet, CharacterInfo>
    ) : CharacterRepository {

    override suspend fun getCharacters(limit: Int, offset: Int): List<CharacterInfo> {
        val dbResult = dao.getAll(limit, offset)
        if (dbResult.isNotEmpty()) {
            return databaseMapper.fromList(dbResult)
        }

        val netResult = networkApi.getCharacters(limit, offset)
        val data = netResult.data?.results ?: listOf()
        val convertedData = networkMapper.fromList(data)
        if (data.isNotEmpty()) {
            dao.insertAll(databaseMapper.toList(convertedData))
        }
        return convertedData
    }

    override suspend fun getCharacter(id: Int): CharacterInfo? {
        val dbResult = dao.getId(id)
        if (dbResult != null) {
            return databaseMapper.fromItem(dbResult)
        }

        val netResult = networkApi.getCharacter(id)
        netResult.data?.results?.getOrNull(0)?.let {
            val convertedData = networkMapper.fromItem(it)
            dao.insert(databaseMapper.toItem(convertedData))
            return convertedData
        } ?: return null
    }
}
