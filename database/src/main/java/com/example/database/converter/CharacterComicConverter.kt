package com.example.database.converter

import androidx.room.TypeConverter
import com.example.common.characters.comic.CharacterComic
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CharacterComicConverter {

    @TypeConverter
    fun fromComic(value: CharacterComic): String {
        val type = object : TypeToken<CharacterComic>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toComic(value: String): CharacterComic {
        val type = object : TypeToken<CharacterComic>() {}.type
        return Gson().fromJson(value, type)
    }
}
