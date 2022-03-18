package com.example.data.datasource.network.model.character.comic

data class CharacterComicNet(
    val available: Int?,
    val returned: Int?,
    val items: List<CharacterComicInfoNet>?
) {
    companion object {
        fun empty(): CharacterComicNet =
            CharacterComicNet(null, null, null)
    }
}
