package com.example.marvelapiapp.navigation

import com.example.domain.model.character.CharacterInfo

interface INavigationManager {
    fun navigateToCharacterDetail(characterInfo: CharacterInfo?, id: Int? = null)
    fun navigateToMainScreen()
    fun navigateToMainFragment()
    fun popBack()
    fun showErrorOverlay()
}
