package com.example.marvelapiapp.viewmodel.characterInfo

import androidx.lifecycle.viewModelScope
import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.response.UseCaseResponse
import com.example.domain.usecase.character.info.GetCharacterInfoUseCase
import com.example.marvelapiapp.databinding.setError
import com.example.marvelapiapp.databinding.setLoading
import com.example.marvelapiapp.databinding.setSuccess
import com.example.marvelapiapp.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterInfoViewModel
    @Inject constructor(
            private val getCharacterInfoUseCase: GetCharacterInfoUseCase
        ) : BaseViewModel() {

    private val characterInfoFlow = MutableStateFlow(UseCaseResponse.notStarted<CharacterInfo>())

    private val _characterData = MutableStateFlow<CharacterInfo?>(null)
    val characterData get() = _characterData.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()

    fun setCharacterData(characterInfo: CharacterInfo) {
        _characterData.value = characterInfo
    }

    fun requestCharacterData(id: Int) {
        characterInfoFlow.setLoading()
        viewModelScope.launch {
            getCharacterInfoUseCase.execute(id, {
                notifyLoadFinished()
                characterInfoFlow.setSuccess(it.data)
            }, {
                notifyLoadFinished()
                characterInfoFlow.setError(it)
            })
        }
    }

    fun getCharacterState(): StateFlow<UseCaseResponse<CharacterInfo>> = characterInfoFlow

    fun notifyLoadFinished() {
        _isLoading.value = false
    }
}
