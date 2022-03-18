package com.example.data.datasource.database.mapper

import com.example.domain.mapper.EntityMapper
import com.example.domain.model.character.CharacterParamInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CharacterParamCacheMapper : EntityMapper<String, List<CharacterParamInfo>> {

    override fun fromItem(from: String): List<CharacterParamInfo> {
        val type = object : TypeToken<List<CharacterParamInfo>>() {}.type
        return Gson().fromJson(from, type)
    }

    override fun toItem(to: List<CharacterParamInfo>): String {
        val type = object : TypeToken<List<CharacterParamInfo>>() {}.type
        return Gson().toJson(to, type)
    }

    override fun fromList(from: List<String>): List<List<CharacterParamInfo>> =
        from.map { fromItem(it) }

    override fun toList(to: List<List<CharacterParamInfo>>): List<String> =
        to.map { toItem(it) }
}
