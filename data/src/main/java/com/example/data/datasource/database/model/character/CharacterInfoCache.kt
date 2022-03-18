package com.example.data.datasource.database.model.character

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain.constant.DatabaseNames

@Entity(indices = [
    Index(value = ["characterId"], unique = true)
], tableName = DatabaseNames.CHARACTER_DATABASE)
data class CharacterInfoCache (
    @PrimaryKey(autoGenerate = true)
    val uniqueId: Int = 0,
    val characterId: Int?,
    val name: String?,
    val description: String?,
    val imageUrl: String?,
    val comics: String?,
    val events: String?,
    val series: String?,
    val stories: String?
)
