package com.example.data.datasource.network.mapper.character.story

import com.example.data.datasource.network.model.character.story.CharacterStoryInfoNet
import com.example.data.datasource.network.model.character.story.CharacterStoryNet
import com.example.domain.mapper.EntityMapper
import com.example.domain.model.character.CharacterParamInfo

class CharacterStoryNetMapper : EntityMapper<CharacterStoryNet, List<CharacterParamInfo>> {

    override fun fromItem(from: CharacterStoryNet): List<CharacterParamInfo> =
        from.items?.map { CharacterParamInfo(it.name ?: "") } ?: listOf()

    override fun toItem(to: List<CharacterParamInfo>): CharacterStoryNet =
        CharacterStoryNet(0, 0, to.map { CharacterStoryInfoNet(it.name, "") })

    override fun fromList(from: List<CharacterStoryNet>): List<List<CharacterParamInfo>> =
        from.map { fromItem(it) }

    override fun toList(to: List<List<CharacterParamInfo>>): List<CharacterStoryNet> =
        to.map { toItem(it) }
}
