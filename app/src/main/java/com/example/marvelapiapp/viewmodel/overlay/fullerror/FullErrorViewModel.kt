package com.example.marvelapiapp.viewmodel.overlay.fullerror

import android.view.View
import com.example.marvelapiapp.databinding.SimpleButtonClickListener
import com.example.marvelapiapp.viewmodel.base.BaseViewModel
import javax.inject.Inject

class FullErrorViewModel
    @Inject constructor() : BaseViewModel(), IFullErrorViewModel {
    private lateinit var listener: SimpleButtonClickListener

    override fun setCloseButtonListener(ls: SimpleButtonClickListener) {
        listener = ls
    }

    override fun handleCloseButton(view: View) {
        listener.onButtonClicked(view)
    }
}
