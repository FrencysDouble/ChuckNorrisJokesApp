package com.example.chucknorrisjokesapp.network

import com.example.chucknorrisjokesapp.di.ApiModule
import com.example.chucknorrisjokesapp.models.BaseModel
import com.example.chucknorrisjokesapp.network.api.ApiResponse
import com.example.chucknorrisjokesapp.network.api.MainScreenApi
import com.example.chucknorrisjokesapp.network.api.handleApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainApiController(private val apiModule : ApiModule) : MainScreenApiInterface {

    private val mainScreenApi: MainScreenApi
        get() = apiModule.provideMainScreenApi()

    override suspend fun getRandomJoke(): Flow<ApiResponse<BaseModel>> =
        flow {
            emit(ApiResponse.Loading)
            val response = handleApiResponse(
                call = {mainScreenApi.getRandomJoke()},
                map = {BaseModel.map(it)}
            )
            emit(response)
        }

}


interface MainScreenApiInterface
{
    suspend fun getRandomJoke() : Flow<ApiResponse<BaseModel>>
}