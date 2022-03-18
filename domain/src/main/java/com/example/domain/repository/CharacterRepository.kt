package com.example.domain.repository

import com.example.domain.model.character.CharacterInfo

interface CharacterRepository {
    suspend fun getCharacters(limit: Int, offset: Int): List<CharacterInfo>
    suspend fun getCharacter(id: Int): CharacterInfo?
}
