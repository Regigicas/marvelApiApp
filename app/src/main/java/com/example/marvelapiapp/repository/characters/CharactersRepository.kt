package com.example.marvelapiapp.repository.characters

import com.example.common.characters.CharacterInfo
import com.example.database.dao.CharacterDao
import com.example.database.entity.CharacterInfoCache
import com.example.marvelapiapp.constant.CharacterInfoRequest
import com.example.marvelapiapp.constant.CharactersRequest
import com.example.marvelapiapp.repository.base.BaseRepository
import com.example.marvelapiapp.repository.base.request.BaseRequest
import com.example.network.characters.CharacterServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CharactersRepository
    @Inject constructor(
        service: CharacterServiceImpl,
        dao: CharacterDao) :
    BaseRepository<CharacterServiceImpl, CharacterDao>(service, dao),
    ICharactersRepository {

    override suspend fun runCharactersCollector(
        state: MutableStateFlow<CharactersRequest>,
        limit: Int,
        offset: Int
    ) {
        val authParams = getAuthParams()
        val request = BaseRequest({
                val dbResult = dao.getAll(limit, offset)
                if (dbResult.isNotEmpty()) {
                    return@BaseRequest dbResult.map { it.info }
                }

                val result = service.getCharacters(authParams.apiKey,
                    authParams.ts, authParams.getHash(), limit, offset)
                val returnValue = result.data?.results ?: listOf()
                dao.insertAll(convertListToCacheModel(returnValue))
                return@BaseRequest returnValue
            }, state)
        request.collect()
    }

    override suspend fun runCharacterCollector(
        state: MutableStateFlow<CharacterInfoRequest>,
        id: Int
    ) {
        val authParams = getAuthParams()
        val request = BaseRequest({
            val dbResult = dao.getId(id)
            if (dbResult != null) {
                return@BaseRequest dbResult.info
            }

            val result = service.getCharacter(id, authParams.apiKey,
                authParams.ts, authParams.getHash())
            val returnValue = result.data?.results?.getOrNull(0)
            if (returnValue != null) {
                dao.insert(convertToCacheModel(returnValue))
            }
            return@BaseRequest returnValue
        }, state)
        request.collect()
    }

    private fun convertListToCacheModel(input: List<CharacterInfo>): List<CharacterInfoCache> =
        CharacterInfoCacheConverter.convertToList(input)

    private fun convertToCacheModel(input: CharacterInfo): CharacterInfoCache =
        CharacterInfoCacheConverter.convertTo(input)
}
