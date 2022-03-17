package com.example.marvelapiapp.repository.base.request

import com.example.marvelapiapp.repository.base.ResponseType
import com.example.marvelapiapp.repository.base.response.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BaseRequest<T>(
    private val request: suspend () -> T,
    private val flowRequest: MutableStateFlow<BaseResponse<T?>>) {

    private fun run() = flow {
        emit(request())
    }.flowOn(Dispatchers.IO)

    suspend fun collect() = run()
        .catch {
            flowRequest.value = BaseResponse(ResponseType.ERROR, null, it)
        } .collect {
            flowRequest.value = BaseResponse(ResponseType.OK, it, null)
        }
}
