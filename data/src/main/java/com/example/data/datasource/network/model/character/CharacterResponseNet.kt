package com.example.data.datasource.network.model.character

data class CharacterResponseNet(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val data: CharacterDataNet?
)
