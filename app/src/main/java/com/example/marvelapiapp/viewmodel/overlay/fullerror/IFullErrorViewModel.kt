package com.example.marvelapiapp.viewmodel.overlay.fullerror

import android.view.View
import com.example.marvelapiapp.databinding.SimpleButtonClickListener

interface IFullErrorViewModel {
    fun setCloseButtonListener(ls: SimpleButtonClickListener)
    fun handleCloseButton(view: View)
}
