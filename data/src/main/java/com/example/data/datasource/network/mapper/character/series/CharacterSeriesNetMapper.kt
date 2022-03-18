package com.example.data.datasource.network.mapper.character.series

import com.example.data.datasource.network.model.character.series.CharacterSeriesInfoNet
import com.example.data.datasource.network.model.character.series.CharacterSeriesNet
import com.example.domain.mapper.EntityMapper
import com.example.domain.model.character.CharacterParamInfo

class CharacterSeriesNetMapper : EntityMapper<CharacterSeriesNet, List<CharacterParamInfo>> {

    override fun fromItem(from: CharacterSeriesNet): List<CharacterParamInfo> =
        from.items?.map { CharacterParamInfo(it.name ?: "") } ?: listOf()

    override fun toItem(to: List<CharacterParamInfo>): CharacterSeriesNet =
        CharacterSeriesNet(0, 0, to.map { CharacterSeriesInfoNet(it.name) })

    override fun fromList(from: List<CharacterSeriesNet>): List<List<CharacterParamInfo>> =
        from.map { fromItem(it) }

    override fun toList(to: List<List<CharacterParamInfo>>): List<CharacterSeriesNet> =
        to.map { toItem(it) }
}
