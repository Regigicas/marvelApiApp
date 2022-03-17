package com.example.database.converter

import androidx.room.TypeConverter
import com.example.common.characters.series.CharacterSeries
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CharacterSeriesConverter {

    @TypeConverter
    fun fromSeries(value: CharacterSeries): String {
        val type = object : TypeToken<CharacterSeries>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toSeries(value: String): CharacterSeries {
        val type = object : TypeToken<CharacterSeries>() {}.type
        return Gson().fromJson(value, type)
    }
}
