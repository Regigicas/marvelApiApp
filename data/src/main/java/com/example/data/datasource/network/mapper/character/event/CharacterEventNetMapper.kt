package com.example.data.datasource.network.mapper.character.event

import com.example.data.datasource.network.model.character.event.CharacterEventInfoNet
import com.example.data.datasource.network.model.character.event.CharacterEventNet
import com.example.domain.mapper.EntityMapper
import com.example.domain.model.character.CharacterParamInfo

class CharacterEventNetMapper : EntityMapper<CharacterEventNet, List<CharacterParamInfo>> {

    override fun fromItem(from: CharacterEventNet): List<CharacterParamInfo> =
        from.items?.map { CharacterParamInfo(it.name ?: "") } ?: listOf()

    override fun toItem(to: List<CharacterParamInfo>): CharacterEventNet =
        CharacterEventNet(0, 0, to.map { CharacterEventInfoNet(it.name) })

    override fun fromList(from: List<CharacterEventNet>): List<List<CharacterParamInfo>> =
        from.map { fromItem(it) }

    override fun toList(to: List<List<CharacterParamInfo>>): List<CharacterEventNet> =
        to.map { toItem(it) }
}
