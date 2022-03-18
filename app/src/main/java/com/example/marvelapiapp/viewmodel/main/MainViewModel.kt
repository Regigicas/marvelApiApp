package com.example.marvelapiapp.viewmodel.main

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.marvelapiapp.databinding.SimpleButtonClickListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private lateinit var listener: SimpleButtonClickListener

    private val _titleText = MutableStateFlow("")
    val titleText get() = _titleText.asStateFlow()

    private val _backVisible = MutableStateFlow(false)
    val backVisible get() = _backVisible.asStateFlow()

    fun setTitleText(text: String) {
        _titleText.value = text
    }

    fun setBackButtonVisible(apply: Boolean) {
        _backVisible.value = apply
    }

    fun onActionBarButtonClicked(view: View) {
        listener.onButtonClicked(view)
    }

    fun setActionBarBackListener(ls: SimpleButtonClickListener) {
        listener = ls
    }
}
