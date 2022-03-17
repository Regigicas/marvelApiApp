package com.example.common.characters.comic

import com.example.common.characters.BaseCharacterParamInfo
import java.io.Serializable

data class CharacterComicInfo(
    val name: String?
) : BaseCharacterParamInfo, Serializable {
    override fun getInfoName(): String? = name
}
