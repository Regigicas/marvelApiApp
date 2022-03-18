package com.example.marvelapiapp.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.domain.constant.ThumbnailAspectRatio
import com.example.marvelapiapp.R
import com.squareup.picasso.Picasso

@BindingAdapter("visible")
fun View.bindVisible(visible: Boolean) {
    if (visible) {
        visible()
    } else {
        gone()
    }
}

@BindingAdapter("imageUrl", "imageRatio")
fun ImageView.bindThumbnail(
    imageUrl: String,
    imageRatio: ThumbnailAspectRatio
) {
    val url = String.format(imageUrl, imageRatio.lowerCase())
    Picasso.get()
        .load(url)
        .fit()
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_filter_drama)
        .error(R.drawable.ic_baseline_error_outline)
        .into(this)
}

@BindingAdapter("refreshListener")
fun SwipeRefreshLayout.bindRefreshListener(refreshFunc: () -> Unit) {
    setOnRefreshListener { refreshFunc() }
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.bindIsRefreshing(active: Boolean) {
    isRefreshing = active
}
