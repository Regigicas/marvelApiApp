package com.example.marvelapiapp.databinding

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapiapp.base.BaseTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class ViewExtensionsTest : BaseTest() {
    override fun testInit() {}

    @Test
    fun `test canRequestMoreData`() {
        val recyclerView = mockk<RecyclerView>(relaxed = true)
        val layoutManager = mockk<LinearLayoutManager>(relaxed = true)
        every { recyclerView.layoutManager } returns layoutManager
        every { layoutManager.itemCount } returns 2
        every { layoutManager.findLastCompletelyVisibleItemPosition() } returns 1

        val result = recyclerView.canRequestMoreData()
        Assert.assertEquals(true, result)
    }

    @Test
    fun `test canRequestMoreData layoutManager is null`() {
        val recyclerView = mockk<RecyclerView>(relaxed = true)
        every { recyclerView.layoutManager } returns null

        val result = recyclerView.canRequestMoreData()
        Assert.assertEquals(false, result)
    }

    @Test
    fun `test canRequestMoreData not at list end`() {
        val recyclerView = mockk<RecyclerView>(relaxed = true)
        val layoutManager = mockk<LinearLayoutManager>(relaxed = true)
        every { recyclerView.layoutManager } returns layoutManager
        every { layoutManager.itemCount } returns 2
        every { layoutManager.findLastCompletelyVisibleItemPosition() } returns 0

        val result = recyclerView.canRequestMoreData()
        Assert.assertEquals(false, result)
    }
}
