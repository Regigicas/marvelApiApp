package com.example.domain.usecase.base

interface UseCase<in I, out O> {
    suspend fun execute(params: I, onResult: (O) -> Unit, onError: (Throwable) -> Unit)
}
