package com.example.data.datasource.network.mapper.character

import com.example.data.datasource.network.model.character.CharacterInfoNet
import com.example.data.datasource.network.model.character.comic.CharacterComicNet
import com.example.data.datasource.network.model.character.event.CharacterEventNet
import com.example.data.datasource.network.model.character.series.CharacterSeriesNet
import com.example.data.datasource.network.model.character.story.CharacterStoryNet
import com.example.data.datasource.network.model.character.thumbnail.CharacterThumbnailNet
import com.example.domain.mapper.EntityMapper
import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.character.CharacterParamInfo

class CharacterInfoNetMapper(
    private val thumbnailMapper: EntityMapper<CharacterThumbnailNet, String>,
    private val comicMapper: EntityMapper<CharacterComicNet, List<CharacterParamInfo>>,
    private val eventMapper: EntityMapper<CharacterEventNet, List<CharacterParamInfo>>,
    private val seriesMapper: EntityMapper<CharacterSeriesNet, List<CharacterParamInfo>>,
    private val storiesMapper: EntityMapper<CharacterStoryNet, List<CharacterParamInfo>>,
    ) : EntityMapper<CharacterInfoNet, CharacterInfo> {

    override fun fromItem(from: CharacterInfoNet): CharacterInfo =
        CharacterInfo(
            id = from.id ?: 0,
            name = getOrEmpty(from.name),
            description = getOrEmpty(from.description),
            imageUrl = thumbnailMapper.fromItem(from.thumbnail ?: CharacterThumbnailNet.empty()),
            comics = comicMapper.fromItem(from.comics ?: CharacterComicNet.empty()),
            events = eventMapper.fromItem(from.events ?: CharacterEventNet.empty()),
            series = seriesMapper.fromItem(from.series ?: CharacterSeriesNet.empty()),
            stories = storiesMapper.fromItem(from.stories ?: CharacterStoryNet.empty()),
        )

    override fun toItem(to: CharacterInfo): CharacterInfoNet =
        CharacterInfoNet(
            id = to.id,
            name = to.name,
            description = to.description,
            thumbnail = thumbnailMapper.toItem(to.imageUrl),
            comics = comicMapper.toItem(to.comics),
            events = eventMapper.toItem(to.events),
            series = seriesMapper.toItem(to.series),
            stories = storiesMapper.toItem(to.stories)
        )

    override fun fromList(from: List<CharacterInfoNet>): List<CharacterInfo> =
        from.map { fromItem(it) }

    override fun toList(to: List<CharacterInfo>): List<CharacterInfoNet> =
        to.map { toItem(it) }

    private fun getOrEmpty(value: String?): String = value ?: ""
}
