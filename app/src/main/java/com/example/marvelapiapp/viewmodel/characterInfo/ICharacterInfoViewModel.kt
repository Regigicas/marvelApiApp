package com.example.marvelapiapp.viewmodel.characterInfo

import com.example.common.characters.CharacterInfo
import com.example.marvelapiapp.constant.CharacterInfoRequest
import kotlinx.coroutines.flow.StateFlow

interface ICharacterInfoViewModel {
    fun setCharacterData(characterInfo: CharacterInfo)
    fun requestCharacterData(id: Int)
    fun getCharacterState(): StateFlow<CharacterInfoRequest>
    fun notifyLoadFinished()
}
