package com.example.marvelapiapp.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.marvelapiapp.R
import com.example.marvelapiapp.databinding.ActivityMainBinding
import com.example.marvelapiapp.databinding.MainActionbarLayoutBinding
import com.example.marvelapiapp.databinding.SimpleButtonClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SimpleButtonClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var barBinding: MainActionbarLayoutBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.setActionBarBackListener(this)

        changeToolbar()
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

    fun setToolbarTitle(text: String) {
        viewModel.setTitleText(text)
    }

    fun setToolbarBackButtonVisible(apply: Boolean) {
        viewModel.setBackButtonVisible(apply)
    }

    override fun onButtonClicked(view: View) {
        when (view.id) {
            R.id.backImageButton -> {
                onBackPressed()
            }
        }
    }
}
