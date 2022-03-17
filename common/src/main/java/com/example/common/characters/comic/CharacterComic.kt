package com.example.common.characters.comic

import java.io.Serializable

data class CharacterComic(
    val available: Int?,
    val returned: Int?,
    val items: List<CharacterComicInfo>?
) : Serializable
