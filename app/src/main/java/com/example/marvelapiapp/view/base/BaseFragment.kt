package com.example.marvelapiapp.view.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment : Fragment() {
    abstract fun getViewModel(): ViewModel
    abstract fun getFragmentName(): String
}
