package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.constants.DatabaseNames
import com.example.database.entity.CharacterInfoCache

@Dao
interface CharacterDao: IAppDao {

    @Query("SELECT * FROM ${DatabaseNames.CHARACTER_DATABASE} LIMIT :limit OFFSET :offset")
    suspend fun getAll(limit: Int, offset: Int): List<CharacterInfoCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cache: List<CharacterInfoCache>)

    @Query("SELECT * FROM ${DatabaseNames.CHARACTER_DATABASE} WHERE uniqueId = :id")
    suspend fun getId(id: Int): CharacterInfoCache?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cache: CharacterInfoCache)

    @Query("DELETE FROM ${DatabaseNames.CHARACTER_DATABASE}")
    suspend fun deleteAll()
}
