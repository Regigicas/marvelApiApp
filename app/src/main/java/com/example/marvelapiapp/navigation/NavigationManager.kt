package com.example.marvelapiapp.navigation

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.common.characters.CharacterInfo
import com.example.marvelapiapp.MainActivity
import com.example.marvelapiapp.R
import com.example.marvelapiapp.constant.CharactersConstant
import com.example.marvelapiapp.view.characterInfo.CharacterInfoFragment
import com.example.marvelapiapp.view.characters.CharactersFragment
import com.example.marvelapiapp.view.overlay.fullerror.FullErrorFragment

object NavigationManager : INavigationManager {
    private lateinit var currentActivity: MainActivity

    override fun navigateToCharacterDetail(characterInfo: CharacterInfo?, id: Int?) {
        if (getCurrentFragment() !is CharacterInfoFragment) {
            val fragment = CharacterInfoFragment()
            val bundle = bundleOf(
                Pair(CharactersConstant.CHARACTER_INFO_KEY, characterInfo),
                Pair(CharactersConstant.CHARACTER_ID_KEY, id))
            fragment.arguments = bundle
            currentActivity.startChildFragment(fragment)
        }
    }

    override fun navigateToMainScreen() {
        val intent = Intent(currentActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        if (!currentActivity.isFinishing) {
            currentActivity.startActivity(intent)
        }
        currentActivity.overridePendingTransition(0, 0)
        currentActivity.finish()
    }

    override fun navigateToMainFragment() {
        if (getCurrentFragment() !is CharactersFragment) {
            currentActivity.startMainFragment()
        }
    }

    override fun popBack() {
        currentActivity.supportFragmentManager.popBackStack()
    }

    override fun showErrorOverlay() {
        val fragment = FullErrorFragment()
        fragment.show(currentActivity.supportFragmentManager, fragment.getFragmentName())
    }

    fun setCurrentActivity(activity: MainActivity) {
        currentActivity = activity
    }

    private fun getCurrentFragment(): Fragment? = currentActivity
        .supportFragmentManager.findFragmentById(R.id.fragmentContainer)
}
