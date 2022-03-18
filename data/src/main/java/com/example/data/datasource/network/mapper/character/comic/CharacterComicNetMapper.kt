package com.example.data.datasource.network.mapper.character.comic

import com.example.data.datasource.network.model.character.comic.CharacterComicInfoNet
import com.example.data.datasource.network.model.character.comic.CharacterComicNet
import com.example.domain.mapper.EntityMapper
import com.example.domain.model.character.CharacterParamInfo

class CharacterComicNetMapper : EntityMapper<CharacterComicNet, List<CharacterParamInfo>> {

    override fun fromItem(from: CharacterComicNet): List<CharacterParamInfo> =
        from.items?.map { CharacterParamInfo(it.name ?: "") } ?: listOf()

    override fun toItem(to: List<CharacterParamInfo>): CharacterComicNet =
        CharacterComicNet(0, 0, to.map { CharacterComicInfoNet(it.name) })

    override fun fromList(from: List<CharacterComicNet>): List<List<CharacterParamInfo>> =
        from.map { fromItem(it) }

    override fun toList(to: List<List<CharacterParamInfo>>): List<CharacterComicNet> =
        to.map { toItem(it) }
}
