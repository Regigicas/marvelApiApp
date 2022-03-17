package com.example.common.characters.event

import java.io.Serializable

data class CharacterEvent(
    val available: Int?,
    val returned: Int?,
    val items: List<CharacterEventInfo>?
) : Serializable
