package com.example.data.datasource.network.model.character.thumbnail

data class CharacterThumbnailNet(
    val path: String?,
    val extension: String?
) {
    companion object {
        fun empty(): CharacterThumbnailNet =
            CharacterThumbnailNet(null, null)
    }
}
