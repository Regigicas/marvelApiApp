package com.example.data.datasource.network.model.character

data class CharacterDataNet(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<CharacterInfoNet>
)
