package com.example.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.common.characters.CharacterInfo
import com.example.database.constants.DatabaseNames

@Entity(indices = [
    Index(value = ["characterId"], unique = true)
], tableName = DatabaseNames.CHARACTER_DATABASE)
data class CharacterInfoCache (
    @PrimaryKey(autoGenerate = true)
    val uniqueId: Int = 0,
    val characterId: Int?,
    @Embedded
    val info: CharacterInfo
)
