package com.example.marvelapiapp.injection

import com.example.data.datasource.network.manager.CharacterApi
import com.example.data.util.MD5Generator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.marvelapiapp.BuildConfig
import com.example.marvelapiapp.constant.AuthConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesUrl(): String = BuildConfig.API_URL

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun providesHttpClient(bodyInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain -> getApiAuthParameters(chain) }
            .addInterceptor(bodyInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(url: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesCharacterApi(retrofit: Retrofit): CharacterApi =
        retrofit.create(CharacterApi::class.java)

    private fun getApiAuthParameters(chain: Interceptor.Chain): okhttp3.Response {
        val ts = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()).toString()
        val oldRequest = chain.request()
        val builder = oldRequest.url.newBuilder()
        builder.addQueryParameter(AuthConstants.FIELD_APIKEY, BuildConfig.PUBLIC_KEY)
            .addQueryParameter(AuthConstants.FIELD_TS, ts)
            .addQueryParameter(AuthConstants.FIELD_HASH,
                MD5Generator.runFor(BuildConfig.PUBLIC_KEY, ts, BuildConfig.PRIVATE_KEY))

        return chain.proceed(oldRequest.newBuilder().url(builder.build()).build())
    }
}
