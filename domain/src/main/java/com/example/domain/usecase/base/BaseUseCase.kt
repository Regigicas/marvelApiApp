package com.example.domain.usecase.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<in I, out O> : UseCase<I, O> {
    abstract suspend fun run(params: I): O

    private fun generateFlow(params: I) = flow {
        emit(run(params))
    }.flowOn(Dispatchers.IO)

    override suspend fun execute(params: I, onResult: (O) -> Unit, onError: (Throwable) -> Unit) {
        generateFlow(params)
            .catch {
                onError(it)
            }
            .collect {
                onResult(it)
            }
    }
}
