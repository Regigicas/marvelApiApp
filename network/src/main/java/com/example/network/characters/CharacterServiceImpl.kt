package com.example.network.characters

import com.example.common.characters.CharacterResponse
import com.example.network.base.INetworkService
import javax.inject.Inject

class CharacterServiceImpl
    @Inject constructor(private val service: CharacterService) : INetworkService {
    suspend fun getCharacters(apiKey: String, ts: Long, hash: String, limit: Int, offset: Int):
            CharacterResponse = service.getCharacters(apiKey, ts, hash, limit, offset)

    suspend fun getCharacter(id: Int, apiKey: String, ts: Long, hash: String):
            CharacterResponse = service.getCharacter(id, apiKey, ts, hash)
}
