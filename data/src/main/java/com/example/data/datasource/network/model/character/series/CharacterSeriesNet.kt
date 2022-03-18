package com.example.data.datasource.network.model.character.series

data class CharacterSeriesNet(
    val available: Int?,
    val returned: Int?,
    val items: List<CharacterSeriesInfoNet>?
) {
    companion object {
        fun empty(): CharacterSeriesNet =
            CharacterSeriesNet(null, null, null)
    }
}
