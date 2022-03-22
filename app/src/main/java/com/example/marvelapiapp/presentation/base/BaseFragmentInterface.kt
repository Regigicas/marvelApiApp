package com.example.marvelapiapp.presentation.base

import androidx.lifecycle.ViewModel

interface BaseFragmentInterface {
    fun getViewModel(): ViewModel
    fun getFragmentName(): String
}
