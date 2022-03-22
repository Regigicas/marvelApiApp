package com.example.marvelapiapp.presentation.fullscreenerror

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.marvelapiapp.databinding.OverlayFullErrorBinding
import com.example.marvelapiapp.databinding.SimpleButtonClickListener
import com.example.marvelapiapp.presentation.base.BaseFragmentInterface

class FullScreenErrorDialogFragment : DialogFragment(), BaseFragmentInterface, SimpleButtonClickListener {
    private val viewModel: FullScreenErrorViewModel by viewModels()

    private var _binding: OverlayFullErrorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OverlayFullErrorBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        isCancelable = false
        viewModel.setCloseButtonListener(this)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel(): ViewModel = viewModel
    override fun getFragmentName(): String = this::class.java.simpleName

    override fun onButtonClicked(view: View) {
        dismiss()
    }
}
