package com.example.data.datasource.network.manager

import com.example.data.datasource.network.model.character.CharacterResponseNet

class CharacterApiImpl(private val characterApi: CharacterApi) {
    suspend fun getCharacters(limit: Int, offset: Int):
            CharacterResponseNet = characterApi.getCharacters(limit, offset)

    suspend fun getCharacter(id: Int):
            CharacterResponseNet = characterApi.getCharacter(id)
}
