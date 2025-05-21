package com.android.animeapp.data.repoImpl

import com.android.animeapp.domain.models.animecharactermodel.AnimeCharacterModel
import com.android.animeapp.domain.models.animeplanetmodel.planetModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiService {

    @GET("characters")
    suspend fun getAllCharacter(
       @Query("limit") limit: Int = 50
    ): Response<AnimeCharacterModel>

    @GET("planets")
    suspend fun getAllPlanets(

    ): Response<planetModel>
}