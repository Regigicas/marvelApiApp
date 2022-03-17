package com.example.marvelapiapp.repository.characters

import com.example.marvelapiapp.constant.CharacterInfoRequest
import com.example.marvelapiapp.constant.CharactersRequest
import kotlinx.coroutines.flow.MutableStateFlow

interface ICharactersRepository {
    suspend fun runCharactersCollector(
        state: MutableStateFlow<CharactersRequest>,
        limit: Int,
        offset: Int
    )
    suspend fun runCharacterCollector(
        state: MutableStateFlow<CharacterInfoRequest>,
        id: Int
    )
}
