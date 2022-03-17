package com.example.common.characters.event

import com.example.common.characters.BaseCharacterParamInfo
import java.io.Serializable

data class CharacterEventInfo(
    val name: String?
) : BaseCharacterParamInfo, Serializable {
    override fun getInfoName(): String? = name
}
