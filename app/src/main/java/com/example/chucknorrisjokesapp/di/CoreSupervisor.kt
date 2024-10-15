package com.example.chucknorrisjokesapp.di

import android.content.Context
import com.example.chucknorrisjokesapp.network.MainApiController

class CoreSupervisor(private val core : CoreInterface) {

    private val context: Context
        get() = core.context()

    private val controllersModule : ControllersModule
        get() = core.controllerModule()

    private val apiModule : ApiModule
        get() = core.apiModule()

    private val coreModule : CoreModule
        get() = core.coreModule()


    inner class ApplicationMain
    {
        fun initArchitecture()
        {
            val mapi = MainApiController(apiModule)

            controllersModule.mApiController(mapi)

            val cacheDataManager = coreModule.provideCacheDataManager(context = context)

            controllersModule.provideCacheManager(cacheDataManager)

            apiModule.retrofit(coreModule.provideRetrofit())
        }

        fun getAppControllers() : ControllersModule = core.controllerModule()
    }
}