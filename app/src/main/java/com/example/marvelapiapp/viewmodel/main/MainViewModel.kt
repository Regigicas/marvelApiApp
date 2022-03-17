package com.example.marvelapiapp.viewmodel.main

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.marvelapiapp.databinding.SimpleButtonClickListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel(), IMainViewModel {
    private lateinit var listener: SimpleButtonClickListener

    private val _titleText = MutableStateFlow("")
    val titleText: StateFlow<String> get() = _titleText

    private val _backVisible = MutableStateFlow(false)
    val backVisible: StateFlow<Boolean> get() = _backVisible

    override fun setTitleText(text: String) {
        _titleText.value = text
    }

    override fun setBackButtonVisible(apply: Boolean) {
        _backVisible.value = apply
    }

    override fun onActionBarButtonClicked(view: View) {
        listener.onButtonClicked(view)
    }

    override fun setActionBarBackListener(ls: SimpleButtonClickListener) {
        listener = ls
    }
}
