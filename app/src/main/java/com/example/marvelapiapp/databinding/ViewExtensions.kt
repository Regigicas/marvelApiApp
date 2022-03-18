package com.example.marvelapiapp.databinding

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.response.UseCaseResponse
import kotlinx.coroutines.flow.MutableStateFlow

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun <T> MutableStateFlow<UseCaseResponse<T>>.setLoading() {
    value = UseCaseResponse.loading()
}

fun <T> MutableStateFlow<UseCaseResponse<T>>.setSuccess(data: T?) {
    value = UseCaseResponse.success(data)
}

fun <T> MutableStateFlow<UseCaseResponse<T>>.setError(err: Throwable?) {
    value = UseCaseResponse.error(err)
}

fun <T> MutableStateFlow<UseCaseResponse<T>>.setFrom(toCopy: UseCaseResponse<T>) {
    value = toCopy
}

fun RecyclerView.canRequestMoreData(): Boolean {
    val linearManager = layoutManager as? LinearLayoutManager
    return linearManager != null &&
            linearManager.findLastCompletelyVisibleItemPosition() == linearManager.itemCount - 1
}
