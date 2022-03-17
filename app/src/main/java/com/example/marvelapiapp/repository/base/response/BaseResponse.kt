package com.example.marvelapiapp.repository.base.response

import com.example.marvelapiapp.repository.base.ResponseType

data class BaseResponse<T>(
    val type: ResponseType,
    val data: T?,
    val error: Throwable?
)
