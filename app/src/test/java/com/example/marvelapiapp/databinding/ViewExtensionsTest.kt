package com.example.marvelapiapp.databinding

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapiapp.base.BaseTest
import com.example.marvelapiapp.repository.base.ResponseType
import com.example.marvelapiapp.repository.base.response.BaseResponse
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert
import org.junit.Test

class ViewExtensionsTest : BaseTest() {
    override fun testInit() {}

    @Test
    fun `test setLoading`() {
        val flow = MutableStateFlow(
            BaseResponse(ResponseType.NOT_STARTED, false, null)
        )
        flow.setLoading()

        Assert.assertEquals(ResponseType.LOADING, flow.value.type)
        Assert.assertEquals(null, flow.value.data)
        Assert.assertEquals(null, flow.value.error)
    }

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
