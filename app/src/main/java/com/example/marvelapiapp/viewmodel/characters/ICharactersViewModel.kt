package com.example.marvelapiapp.viewmodel.characters

import com.example.marvelapiapp.constant.CharactersRequest
import kotlinx.coroutines.flow.StateFlow

interface ICharactersViewModel {
    fun collectCharacters()
    fun getCharactersState(): StateFlow<CharactersRequest>
    fun loadMoreCharacters()
    fun handleServiceError(hasData: Boolean)
    fun refreshData()
    fun checkDataResetAndClear(): Boolean
    fun notifyLoadFinished()
}
