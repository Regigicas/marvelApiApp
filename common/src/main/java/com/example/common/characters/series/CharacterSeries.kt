package com.example.common.characters.series

import java.io.Serializable

data class CharacterSeries(
    val available: Int?,
    val returned: Int?,
    val items: List<CharacterSeriesInfo>?
) : Serializable
