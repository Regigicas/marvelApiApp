package com.example.data.datasource.network.model.character.story

data class CharacterStoryNet(
    val available: Int?,
    val returned: Int?,
    val items: List<CharacterStoryInfoNet>?
) {
    companion object {
        fun empty(): CharacterStoryNet =
            CharacterStoryNet(null, null, null)
    }
}
