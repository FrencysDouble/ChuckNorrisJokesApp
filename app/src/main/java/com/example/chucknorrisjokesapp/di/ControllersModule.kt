package com.example.chucknorrisjokesapp.di

import com.example.chucknorrisjokesapp.cache.CacheDataManager
import com.example.chucknorrisjokesapp.controllers.MainScreenController
import com.example.chucknorrisjokesapp.network.MainApiController
import com.example.chucknorrisjokesapp.network.MainScreenApiInterface
import com.github.klee0kai.stone.annotations.module.BindInstance
import com.github.klee0kai.stone.annotations.module.Module
import com.github.klee0kai.stone.annotations.module.Provide

@Module
interface ControllersModule {

    @BindInstance
    fun mApiController(ma : MainApiController? = null) : MainApiController


    @Provide(cache = Provide.CacheType.Soft)
    fun provideMainScreenController(mainMenuApiInterface: MainScreenApiInterface = mApiController(),cacheDataManager : CacheDataManager = provideCacheManager()) : MainScreenController

    @BindInstance
    fun provideCacheManager(cacheDataManager: CacheDataManager? = null) : CacheDataManager
}