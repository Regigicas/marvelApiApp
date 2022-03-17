package com.example.database.converter

import androidx.room.TypeConverter
import com.example.common.characters.story.CharacterStory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CharacterStoryConverter {

    @TypeConverter
    fun fromStory(value: CharacterStory): String {
        val type = object : TypeToken<CharacterStory>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toStory(value: String): CharacterStory {
        val type = object : TypeToken<CharacterStory>() {}.type
        return Gson().fromJson(value, type)
    }
}
