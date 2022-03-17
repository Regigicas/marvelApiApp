package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.converter.CharacterComicConverter
import com.example.database.converter.CharacterEventConverter
import com.example.database.converter.CharacterSeriesConverter
import com.example.database.converter.CharacterStoryConverter
import com.example.database.converter.CharacterThumbnailConverter
import com.example.database.dao.CharacterDao
import com.example.database.entity.CharacterInfoCache

@Database(
    entities = [CharacterInfoCache::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    CharacterThumbnailConverter::class,
    CharacterComicConverter::class,
    CharacterStoryConverter::class,
    CharacterEventConverter::class,
    CharacterSeriesConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao
}
