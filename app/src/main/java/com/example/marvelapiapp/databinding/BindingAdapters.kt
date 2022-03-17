package com.example.marvelapiapp.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.common.characters.thumbnail.CharacterThumbnail
import com.example.common.characters.thumbnail.ThumbnailAspectRatio
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
    imageUrl: CharacterThumbnail,
    imageRatio: ThumbnailAspectRatio
) {
    var urlPath = imageUrl.path
    urlPath?.let {
        if (!it.contains("https")) // The api seems to return http instead of https
            urlPath = it.replace("http", "https")
    }
    val url = "${urlPath}/${imageRatio.toApiName()}.${imageUrl.extension}"
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
