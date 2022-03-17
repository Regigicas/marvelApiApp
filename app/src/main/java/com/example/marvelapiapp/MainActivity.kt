package com.example.marvelapiapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import com.example.marvelapiapp.constant.MainConstants
import com.example.marvelapiapp.databinding.MainActionbarLayoutBinding
import com.example.marvelapiapp.databinding.SimpleButtonClickListener
import com.example.marvelapiapp.navigation.NavigationManager
import com.example.marvelapiapp.view.base.BaseFragment
import com.example.marvelapiapp.view.characters.CharactersFragment
import com.example.marvelapiapp.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IMainView, SimpleButtonClickListener {
    private lateinit var barBinding: MainActionbarLayoutBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.setActionBarBackListener(this)
        NavigationManager.setCurrentActivity(this)

        changeToolbar()
        val restoredFromSave = savedInstanceState?.getBoolean(
            MainConstants.RESTORED_FROM_BUNDLE_KEY) ?: false
        if (!restoredFromSave) {
            NavigationManager.navigateToMainFragment()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(MainConstants.RESTORED_FROM_BUNDLE_KEY, true)
    }

    private fun changeToolbar() {
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setDisplayShowCustomEnabled(true)
            setCustomView(R.layout.main_actionbar_layout)
            barBinding = MainActionbarLayoutBinding.bind(customView)
        }
        barBinding.viewModel = viewModel
        barBinding.lifecycleOwner = this
        val toolbarParent = supportActionBar?.customView?.parent as? Toolbar
        toolbarParent?.let {
            it.setPadding(0, 0, 0, 0)
            it.setContentInsetsAbsolute(0, 0)
        }

        setToolbarTitle(resources.getString(R.string.title_list_of_characters))
    }

    override fun setToolbarTitle(text: String) {
        viewModel.setTitleText(text)
    }

    override fun setToolbarVisible(apply: Boolean) {
        if (apply) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
    }

    override fun setToolbarBackButtonVisible(apply: Boolean) {
        viewModel.setBackButtonVisible(apply)
    }

    fun startMainFragment() {
        val mainFragment = CharactersFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, mainFragment)
            .addToBackStack(null)
            .commit()
    }

    fun startChildFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(fragment.getFragmentName())
            .commit()
    }

    override fun onButtonClicked(view: View) {
        when (view.id) {
            R.id.backImageButton -> {
                onBackPressed()
            }
        }
    }
}
