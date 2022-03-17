package com.example.marvelapiapp.viewmodel.characters

import androidx.lifecycle.viewModelScope
import com.example.marvelapiapp.BuildConfig
import com.example.marvelapiapp.constant.CharactersRequest
import com.example.marvelapiapp.constant.CharactersConstant
import com.example.marvelapiapp.databinding.setLoading
import com.example.marvelapiapp.navigation.NavigationManager
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
class CharactersViewModel
    @Inject constructor(private val repository: CharactersRepository)
        : BaseViewModel(), ICharactersViewModel {

    private val charactersFlow = MutableStateFlow<
            CharactersRequest>(BaseResponse(ResponseType.NOT_STARTED, null, null))

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    val isDebugBuild: StateFlow<Boolean> = MutableStateFlow(BuildConfig.DEBUG)

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    private val _errorVisible = MutableStateFlow(false)
    val errorVisible: StateFlow<Boolean> get() = _errorVisible

    private var currentOffset = 0
    private var needsDataReset = false

    override fun collectCharacters() {
        launchCollector(true)
    }

    override fun getCharactersState(): StateFlow<CharactersRequest> = charactersFlow

    override fun loadMoreCharacters() {
        if (_isLoading.value || _isRefreshing.value) {
            return
        }

        launchCollector(false)
    }

    override fun handleServiceError(hasData: Boolean) {
        if (hasData) {
            NavigationManager.showErrorOverlay()
        } else {
            _errorVisible.value = true
        }
    }

    override fun refreshData() {
        _errorVisible.value = false
        _isRefreshing.value = true
        needsDataReset = true
        launchCollector(true)
        _isRefreshing.value = false
    }

    override fun checkDataResetAndClear(): Boolean {
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
            repository.runCharactersCollector(charactersFlow,
                CharactersConstant.CHARACTERS_LIMIT, currentOffset)
            currentOffset += CharactersConstant.OFFSET_PER_PAGE
        }
    }

    override fun notifyLoadFinished() {
        _isLoading.value = false
    }
}
