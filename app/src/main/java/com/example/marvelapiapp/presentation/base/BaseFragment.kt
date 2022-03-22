package com.example.marvelapiapp.presentation.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment(), BaseFragmentInterface {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setBinding(newBinding: T) {
        _binding = newBinding
    }
}
