package com.example.data.datasource.network.model.character.event

data class CharacterEventNet(
    val available: Int?,
    val returned: Int?,
    val items: List<CharacterEventInfoNet>?
) {
    companion object {
        fun empty(): CharacterEventNet =
            CharacterEventNet(null, null, null)
    }
}
