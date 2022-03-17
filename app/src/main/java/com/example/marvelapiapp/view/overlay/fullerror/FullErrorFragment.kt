package com.example.marvelapiapp.view.overlay.fullerror

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.marvelapiapp.MainActivity
import com.example.marvelapiapp.databinding.OverlayFullErrorBinding
import com.example.marvelapiapp.databinding.SimpleButtonClickListener
import com.example.marvelapiapp.view.base.IBaseFragment
import com.example.marvelapiapp.viewmodel.overlay.fullerror.FullErrorViewModel

class FullErrorFragment : DialogFragment(), IBaseFragment, SimpleButtonClickListener {
    private val viewModel: FullErrorViewModel by viewModels()
    private lateinit var binding: OverlayFullErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OverlayFullErrorBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        isCancelable = false
        viewModel.setCloseButtonListener(this)
        // (activity as? MainActivity)?.hideToolbar()
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

    /*override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.showToolbar()
    }*/

    override fun getViewModel(): ViewModel = viewModel
    override fun getFragmentName(): String = this::class.java.simpleName

    override fun onButtonClicked(view: View) {
        dismiss()
    }
}
