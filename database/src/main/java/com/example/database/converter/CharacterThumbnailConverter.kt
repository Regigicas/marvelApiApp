package com.example.database.converter

import androidx.room.TypeConverter
import com.example.common.characters.thumbnail.CharacterThumbnail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CharacterThumbnailConverter {

    @TypeConverter
    fun fromThumbnail(value: CharacterThumbnail): String {
        val type = object : TypeToken<CharacterThumbnail>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toThumbnail(value: String): CharacterThumbnail {
        val type = object : TypeToken<CharacterThumbnail>() {}.type
        return Gson().fromJson(value, type)
    }
}
