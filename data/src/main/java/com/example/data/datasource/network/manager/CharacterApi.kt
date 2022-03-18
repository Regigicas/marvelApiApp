package com.example.data.datasource.network.manager

import com.example.data.datasource.network.model.character.CharacterResponseNet
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : CharacterResponseNet

    @GET("characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int) : CharacterResponseNet
}
