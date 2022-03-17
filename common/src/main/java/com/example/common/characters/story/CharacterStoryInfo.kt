package com.example.common.characters.story

import com.example.common.characters.BaseCharacterParamInfo
import java.io.Serializable

class CharacterStoryInfo(
    val name: String?,
    val type: String?
) : BaseCharacterParamInfo, Serializable {
    override fun getInfoName(): String? = name
}
