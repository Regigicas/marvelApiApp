package com.example.marvelapiapp.view.characters.listener

import com.example.domain.model.character.CharacterInfo

interface CharacterItemListener {
    fun onCharacterClicked(characterInfo: CharacterInfo)
}
