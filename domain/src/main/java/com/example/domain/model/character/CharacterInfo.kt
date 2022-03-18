package com.example.domain.model.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterInfo(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val comics: List<CharacterParamInfo>,
    val events: List<CharacterParamInfo>,
    val series: List<CharacterParamInfo>,
    val stories: List<CharacterParamInfo>
) : Parcelable {
    companion object {
        fun empty() = CharacterInfo(0, "", "", "",
            listOf(), listOf(), listOf(), listOf())
    }
}
