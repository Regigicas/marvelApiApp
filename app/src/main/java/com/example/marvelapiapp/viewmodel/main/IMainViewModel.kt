package com.example.marvelapiapp.viewmodel.main

import android.view.View
import com.example.marvelapiapp.databinding.SimpleButtonClickListener

interface IMainViewModel {
    fun setTitleText(text: String)
    fun setBackButtonVisible(apply: Boolean)
    fun onActionBarButtonClicked(view: View)
    fun setActionBarBackListener(ls: SimpleButtonClickListener)
}
