package com.example.marvelapiapp.databinding

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapiapp.repository.base.ResponseType
import com.example.marvelapiapp.repository.base.response.BaseResponse
import kotlinx.coroutines.flow.MutableStateFlow

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun <T> MutableStateFlow<BaseResponse<T>>.setLoading() {
    value = BaseResponse(ResponseType.LOADING, null, null)
}

fun RecyclerView.canRequestMoreData(): Boolean {
    val linearManager = layoutManager as? LinearLayoutManager
    return linearManager != null &&
            linearManager.findLastCompletelyVisibleItemPosition() == linearManager.itemCount - 1
}
