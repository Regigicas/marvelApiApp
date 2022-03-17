package com.example.common.characters.series

import com.example.common.characters.BaseCharacterParamInfo
import java.io.Serializable

data class CharacterSeriesInfo(
    val name: String?
) : BaseCharacterParamInfo, Serializable {
    override fun getInfoName(): String? = name
}
