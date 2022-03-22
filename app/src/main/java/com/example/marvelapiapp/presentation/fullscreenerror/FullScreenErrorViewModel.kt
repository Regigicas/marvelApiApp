package com.example.marvelapiapp.presentation.fullscreenerror

import android.view.View
import com.example.marvelapiapp.databinding.SimpleButtonClickListener
import com.example.marvelapiapp.presentation.base.BaseViewModel
import javax.inject.Inject

class FullScreenErrorViewModel
    @Inject constructor() : BaseViewModel() {
    private lateinit var listener: SimpleButtonClickListener

    fun setCloseButtonListener(ls: SimpleButtonClickListener) {
        listener = ls
    }

    fun handleCloseButton(view: View) {
        listener.onButtonClicked(view)
    }
}
