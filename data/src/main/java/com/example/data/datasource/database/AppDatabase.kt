package com.example.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.datasource.database.dao.CharacterDao
import com.example.data.datasource.database.model.character.CharacterInfoCache

@Database(
    entities = [CharacterInfoCache::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao
}
