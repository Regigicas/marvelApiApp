package com.example.marvelapiapp.repository.base

import com.example.database.dao.IAppDao
import com.example.marvelapiapp.BuildConfig
import com.example.network.base.INetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository<T : INetworkService, D : IAppDao>
    constructor(protected val service: T, protected val dao: D) {

    companion object {
        const val IN_SECONDS = 1000
    }

    fun getAuthParams(): AuthParams {
        return AuthParams(apiKey = BuildConfig.PUBLIC_KEY,
            ts = getCurrentTime(), privateKey = BuildConfig.PRIVATE_KEY)
    }

    private fun getCurrentTime(): Long = System.currentTimeMillis() / IN_SECONDS
}
