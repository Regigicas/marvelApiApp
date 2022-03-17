package com.example.common.characters

import com.example.common.characters.comic.CharacterComic
import com.example.common.characters.event.CharacterEvent
import com.example.common.characters.series.CharacterSeries
import com.example.common.characters.story.CharacterStory
import com.example.common.characters.thumbnail.CharacterThumbnail
import java.io.Serializable

data class CharacterInfo(
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnail: CharacterThumbnail?,
    val comics: CharacterComic?,
    val stories: CharacterStory?,
    val events: CharacterEvent?,
    val series: CharacterSeries?
) : Serializable
