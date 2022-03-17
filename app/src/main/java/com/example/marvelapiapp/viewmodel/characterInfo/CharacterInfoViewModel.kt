package com.example.marvelapiapp.viewmodel.characterInfo

import androidx.lifecycle.viewModelScope
import com.example.common.characters.CharacterInfo
import com.example.marvelapiapp.constant.CharacterInfoRequest
import com.example.marvelapiapp.databinding.setLoading
import com.example.marvelapiapp.repository.base.ResponseType
import com.example.marvelapiapp.repository.base.response.BaseResponse
import com.example.marvelapiapp.repository.characters.CharactersRepository
import com.example.marvelapiapp.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterInfoViewModel
    @Inject constructor(private val repository: CharactersRepository)
        : BaseViewModel(), ICharacterInfoViewModel {

    private val characterInfoFlow = MutableStateFlow<
            CharacterInfoRequest>(BaseResponse(ResponseType.NOT_STARTED, null, null))

    private val _characterData = MutableStateFlow<CharacterInfo?>(null)
    val characterData: StateFlow<CharacterInfo?> get() = _characterData

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    override fun setCharacterData(characterInfo: CharacterInfo) {
        _characterData.value = characterInfo
    }

    override fun requestCharacterData(id: Int) {
        characterInfoFlow.setLoading()
        viewModelScope.launch {
            repository.runCharacterCollector(characterInfoFlow, id)
        }
    }

    override fun getCharacterState(): StateFlow<CharacterInfoRequest> = characterInfoFlow

    override fun notifyLoadFinished() {
        _isLoading.value = false
    }
}
