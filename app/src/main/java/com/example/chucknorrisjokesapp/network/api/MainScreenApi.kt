package com.example.chucknorrisjokesapp.network.api

import com.example.chucknorrisjokesapp.models.BaseModelDTO
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

class MainScreenApi(private val retrofit: Retrofit) {

    private val service : MainScreenApiPoints = retrofit.create(MainScreenApiPoints::class.java)

    suspend fun getRandomJoke() : Response<BaseModelDTO> = service.getRandomJoke()
}


interface MainScreenApiPoints
{
    @GET(ApiRoutes.RANDOM)
    suspend fun getRandomJoke() : Response<BaseModelDTO>
}