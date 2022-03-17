package com.example.marvelapiapp.repository.characters

import com.example.common.characters.CharacterInfo
import com.example.database.entity.CharacterInfoCache

object CharacterInfoCacheConverter {
    fun convertTo(input: CharacterInfo): CharacterInfoCache =
        CharacterInfoCache(characterId = input.id, info = input)

    fun convertToList(input: List<CharacterInfo>): List<CharacterInfoCache> =
        input.map { convertTo(it) }
}
