package com.example.common.characters.thumbnail

import java.io.Serializable

data class CharacterThumbnail(
    val path: String?,
    val extension: String?
) : Serializable
