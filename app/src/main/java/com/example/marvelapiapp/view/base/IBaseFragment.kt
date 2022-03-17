package com.example.marvelapiapp.view.base

import androidx.lifecycle.ViewModel

interface IBaseFragment {
    fun getViewModel(): ViewModel
    fun getFragmentName(): String
}
