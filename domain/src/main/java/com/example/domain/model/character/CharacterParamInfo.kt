package com.example.domain.model.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterParamInfo(
    val name: String
) : Parcelable
