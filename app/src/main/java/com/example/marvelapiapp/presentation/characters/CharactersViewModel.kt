package com.example.marvelapiapp.presentation.characters

import androidx.lifecycle.viewModelScope
import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.response.UseCaseResponse
import com.example.domain.usecase.character.list.GetCharactersUseCase
import com.example.marvelapiapp.BuildConfig
import com.example.marvelapiapp.constant.CharactersConstant
import com.example.marvelapiapp.databinding.setError
import com.example.marvelapiapp.databinding.setFrom
import com.example.marvelapiapp.databinding.setLoading
import com.example.marvelapiapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel
    @Inject constructor(
            private val getCharactersUseCase: GetCharactersUseCase
        ) : BaseViewModel() {

    private val charactersFlow = MutableStateFlow(
        UseCaseResponse.notStarted<List<CharacterInfo>>())

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()

    val isDebugBuild: StateFlow<Boolean> = MutableStateFlow(BuildConfig.DEBUG)

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    private val _errorVisible = MutableStateFlow(false)
    val errorVisible get() = _errorVisible.asStateFlow()

    private var currentOffset = 0
    private var needsDataReset = false

    fun collectCharacters() {
        launchCollector(true)
    }

    fun getCharactersState(): StateFlow<UseCaseResponse<List<CharacterInfo>>> = charactersFlow

    fun loadMoreCharacters() {
        if (_isLoading.value || _isRefreshing.value) {
            return
        }

        launchCollector(false)
    }

    fun notifyServiceError() {
        _errorVisible.value = true
    }

    fun refreshData() {
        _errorVisible.value = false
        _isRefreshing.value = true
        needsDataReset = true
        launchCollector(true)
        _isRefreshing.value = false
    }

    fun checkDataResetAndClear(): Boolean {
        val result = needsDataReset
        needsDataReset = false
        return result
    }

    private fun launchCollector(refreshOffset: Boolean) {
        if (refreshOffset) {
            currentOffset = 0
        }

        _isLoading.value = true
        charactersFlow.setLoading()
        viewModelScope.launch {
            getCharactersUseCase.execute(
                GetCharactersUseCase.GetCharactersParams(
                    CharactersConstant.CHARACTERS_LIMIT,
                    currentOffset
                ), {
                    notifyLoadFinished()
                    charactersFlow.setFrom(it)
                    currentOffset += CharactersConstant.OFFSET_PER_PAGE
                }, {
                    notifyLoadFinished()
                    charactersFlow.setError(it)
                })
        }
    }

    fun notifyLoadFinished() {
        _isLoading.value = false
    }
}
