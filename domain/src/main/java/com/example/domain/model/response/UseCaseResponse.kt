package com.example.domain.model.response

data class UseCaseResponse<T>(
    var status: UseCaseResponseStatus,
    var data: T?,
    var error: Throwable?
) {
    companion object {
        fun <T> notStarted() = UseCaseResponse<T>(UseCaseResponseStatus.NOT_STARTED, null, null)
        fun <T> loading() = UseCaseResponse<T>(UseCaseResponseStatus.LOADING, null, null)
        fun <T> success(input: T?) = UseCaseResponse(UseCaseResponseStatus.OK, input, null)
        fun <T> error(input: Throwable?) = UseCaseResponse<T>(UseCaseResponseStatus.ERROR, null, input)
    }
}
