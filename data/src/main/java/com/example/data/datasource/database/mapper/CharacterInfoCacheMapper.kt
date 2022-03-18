package com.example.data.datasource.database.mapper

import com.example.data.datasource.database.model.character.CharacterInfoCache
import com.example.domain.mapper.EntityMapper
import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.character.CharacterParamInfo

class CharacterInfoCacheMapper(
    private val paramMapper: EntityMapper<String, List<CharacterParamInfo>>
    ) : EntityMapper<CharacterInfoCache, CharacterInfo> {

    override fun fromItem(from: CharacterInfoCache): CharacterInfo =
        CharacterInfo(
            id = from.characterId ?: 0,
            name = getOrEmpty(from.name),
            description = getOrEmpty(from.description),
            imageUrl = getOrEmpty(from.imageUrl),
            comics = paramMapper.fromItem(getOrEmpty(from.comics)),
            events = paramMapper.fromItem(getOrEmpty(from.events)),
            series = paramMapper.fromItem(getOrEmpty(from.series)),
            stories = paramMapper.fromItem(getOrEmpty(from.series))
        )

    override fun toItem(to: CharacterInfo): CharacterInfoCache =
        CharacterInfoCache(
            characterId = to.id,
            name = to.name,
            description = to.description,
            imageUrl = to.imageUrl,
            comics = paramMapper.toItem(to.comics),
            events = paramMapper.toItem(to.events),
            series = paramMapper.toItem(to.series),
            stories = paramMapper.toItem(to.stories)
        )

    override fun fromList(from: List<CharacterInfoCache>): List<CharacterInfo> =
        from.map { fromItem(it) }

    override fun toList(to: List<CharacterInfo>): List<CharacterInfoCache> =
        to.map { toItem(it) }

    private fun getOrEmpty(value: String?): String = value ?: ""
}
