package com.example.marvelapiapp.view.base

import androidx.lifecycle.ViewModel

interface BaseFragmentInterface {
    abstract fun getViewModel(): ViewModel
    abstract fun getFragmentName(): String
}
