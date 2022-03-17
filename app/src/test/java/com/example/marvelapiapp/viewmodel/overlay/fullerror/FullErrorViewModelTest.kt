package com.example.marvelapiapp.viewmodel.overlay.fullerror

import android.view.View
import com.example.marvelapiapp.base.BaseTest
import com.example.marvelapiapp.databinding.SimpleButtonClickListener
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class FullErrorViewModelTest : BaseTest() {
    private lateinit var viewModel: FullErrorViewModel

    override fun testInit() {
        viewModel = spyk()
    }

    @Test
    fun `test onActionBarButtonClicked`() {
        val listener = mockk<SimpleButtonClickListener>(relaxed = true)
        val view = mockk<View>(relaxed = true)
        viewModel.setCloseButtonListener(listener)
        viewModel.handleCloseButton(view)

        verify(exactly = 1) { listener.onButtonClicked(view) }
    }

    @Test
    fun `test setActionBarBackListener`() {
        val listener = mockk<SimpleButtonClickListener>(relaxed = true)
        viewModel.setCloseButtonListener(listener)

        val result = viewModel.getTestValue<SimpleButtonClickListener>("listener")
        Assert.assertEquals(listener, result)
    }
}
