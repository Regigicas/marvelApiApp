package com.example.data.datasource.network.model.character

import com.example.data.datasource.network.model.character.comic.CharacterComicNet
import com.example.data.datasource.network.model.character.event.CharacterEventNet
import com.example.data.datasource.network.model.character.series.CharacterSeriesNet
import com.example.data.datasource.network.model.character.story.CharacterStoryNet
import com.example.data.datasource.network.model.character.thumbnail.CharacterThumbnailNet

data class CharacterInfoNet(
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnail: CharacterThumbnailNet?,
    val comics: CharacterComicNet?,
    val stories: CharacterStoryNet?,
    val events: CharacterEventNet?,
    val series: CharacterSeriesNet?
)
