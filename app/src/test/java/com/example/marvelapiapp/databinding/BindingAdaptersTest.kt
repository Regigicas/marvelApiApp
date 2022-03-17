package com.example.marvelapiapp.databinding

import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.common.characters.thumbnail.CharacterThumbnail
import com.example.common.characters.thumbnail.ThumbnailAspectRatio
import com.example.marvelapiapp.base.BaseTest
import com.squareup.picasso.Picasso
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Test

class BindingAdaptersTest : BaseTest() {
    override fun testInit() {}

    @Test
    fun `test bindVisible true`() {
        val view = mockk<View>(relaxed = true)
        view.bindVisible(true)

        verify(exactly = 1) { view.visible() }
    }

    @Test
    fun `test bindVisible false`() {
        val view = mockk<View>(relaxed = true)
        view.bindVisible(false)

        verify(exactly = 1) { view.gone() }
    }

    @Test
    fun `test bindImageUrl`() {
        val picasso = mockk<Picasso>(relaxed = true)
        mockkStatic(Picasso::class)
        every { Picasso.get() } returns picasso

        val imageView = mockk<ImageView>(relaxed = true)

        imageView.bindThumbnail(
            CharacterThumbnail("https://www.google.es", "png"),
            ThumbnailAspectRatio.LANDSCAPE_AMAZING)

        val string = "https://www.google.es/landscape_amazing.png"
        verify(exactly = 1) { picasso.load(string) }
    }

    @Test
    fun `test bindImageUrl http`() {
        val picasso = mockk<Picasso>(relaxed = true)
        mockkStatic(Picasso::class)
        every { Picasso.get() } returns picasso

        val imageView = mockk<ImageView>(relaxed = true)

        imageView.bindThumbnail(
            CharacterThumbnail("http://www.google.es", "png"),
            ThumbnailAspectRatio.LANDSCAPE_AMAZING)

        val string = "https://www.google.es/landscape_amazing.png"
        verify(exactly = 1) { picasso.load(string) }
    }

    @Test
    fun `test bindRefreshListener`() {
        val refreshLayout = mockk<SwipeRefreshLayout>(relaxed = true)
        refreshLayout.bindRefreshListener {}
        verify(exactly = 1) { refreshLayout.setOnRefreshListener(any()) }
    }

    @Test
    fun `test bindIsRefreshing`() {
        val refreshLayout = mockk<SwipeRefreshLayout>(relaxed = true)
        refreshLayout.bindIsRefreshing(true)
        verify(exactly = 1) { refreshLayout.isRefreshing = true }
    }
}
