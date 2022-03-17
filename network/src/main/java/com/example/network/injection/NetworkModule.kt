package com.example.network.injection

import com.example.network.BuildConfig
import com.example.network.characters.CharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesUrl() = BuildConfig.API_URL

    @Provides
    @Singleton
    fun providesMarvelApiService(url: String): CharacterService =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharacterService::class.java)
}
