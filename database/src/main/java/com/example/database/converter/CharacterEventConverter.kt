package com.example.database.converter

import androidx.room.TypeConverter
import com.example.common.characters.event.CharacterEvent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CharacterEventConverter {

    @TypeConverter
    fun fromEvent(value: CharacterEvent): String {
        val type = object : TypeToken<CharacterEvent>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toEvent(value: String): CharacterEvent {
        val type = object : TypeToken<CharacterEvent>() {}.type
        return Gson().fromJson(value, type)
    }
}
