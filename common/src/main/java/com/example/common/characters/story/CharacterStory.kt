package com.example.common.characters.story

import java.io.Serializable

data class CharacterStory(
    val available: Int?,
    val returned: Int?,
    val items: List<CharacterStoryInfo>?
) : Serializable
