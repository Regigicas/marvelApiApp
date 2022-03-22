package com.example.marvelapiapp.presentation

import android.view.View
import com.example.marvelapiapp.base.BaseTest
import com.example.marvelapiapp.databinding.SimpleButtonClickListener
import com.example.marvelapiapp.presentation.MainViewModel
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert
import org.junit.Test

class MainViewModelTest : BaseTest() {
    private lateinit var viewModel: MainViewModel

    override fun testInit() {
        viewModel = spyk()
    }

    @Test
    fun `test setTitleText`() {
        val text = "testtest"
        viewModel.setTitleText(text)

        val privateValue = viewModel.getTestValue<MutableStateFlow<String>>("_titleText")
        Assert.assertEquals(text, viewModel.titleText.value)
        Assert.assertEquals(privateValue.value, viewModel.titleText.value)
    }

    @Test
    fun `test setBackButtonVisible`() {
        viewModel.setBackButtonVisible(true)

        val privateValue = viewModel.getTestValue<MutableStateFlow<Boolean>>("_backVisible")
        Assert.assertEquals(true, viewModel.backVisible.value)
        Assert.assertEquals(privateValue.value, viewModel.backVisible.value)
    }

    @Test
    fun `test onActionBarButtonClicked`() {
        val listener = mockk<SimpleButtonClickListener>(relaxed = true)
        val view = mockk<View>(relaxed = true)
        viewModel.setActionBarBackListener(listener)
        viewModel.onActionBarButtonClicked(view)

        verify(exactly = 1) { listener.onButtonClicked(view) }
    }

    @Test
    fun `test setActionBarBackListener`() {
        val listener = mockk<SimpleButtonClickListener>(relaxed = true)
        viewModel.setActionBarBackListener(listener)

        val result = viewModel.getTestValue<SimpleButtonClickListener>("listener")
        Assert.assertEquals(listener, result)
    }
}
