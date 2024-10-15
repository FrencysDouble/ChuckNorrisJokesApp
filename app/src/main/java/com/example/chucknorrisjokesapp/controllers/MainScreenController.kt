package com.example.chucknorrisjokesapp.controllers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chucknorrisjokesapp.cache.CacheDataManager
import com.example.chucknorrisjokesapp.models.BaseModel
import com.example.chucknorrisjokesapp.network.MainScreenApiInterface
import com.example.chucknorrisjokesapp.network.api.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenController(
    private val mainScreenApiInterface: MainScreenApiInterface,
    private val cacheDataManager: CacheDataManager
) : ViewModel() {

    private val _jokeData = MutableLiveData<BaseModel>()
    val jokeData: LiveData<BaseModel> get() = _jokeData

    private val _jokeHistory = MutableLiveData<List<String>>()
    val jokeHistory : LiveData<List<String>> get() =_jokeHistory

    val isLoading : MutableLiveData<Boolean> = MutableLiveData(false)

    val isConnectionError : MutableLiveData<Boolean> = MutableLiveData(false)

    val selectedJoke : MutableLiveData<String?> = MutableLiveData()


    fun getData()
    {
        viewModelScope.launch {
            mainScreenApiInterface.getRandomJoke().collect{response ->
                withContext(Dispatchers.Main)
                {
                    when(response)
                    {
                        is ApiResponse.Success -> {
                            _jokeData.value = response.data
                            Log.d("Jokes", "Success: ${response.data}")
                            isLoading.value = false
                            isConnectionError.value = false
                            response.data.value.let { saveJoke(it) }
                        }
                        is ApiResponse.Loading -> {
                            Log.d("Jokes","Loading")
                            _jokeData.value = BaseModel("","","")
                            isLoading.value = true
                        }
                        is ApiResponse.BadRequest ->{
                            Log.d("Jokes",response.message)
                            isLoading.value = false
                        }
                        is ApiResponse.NetworkError -> {
                            Log.d("Jokes","network_error")
                            isLoading.value = false
                            isConnectionError.value = true
                        }
                    }
                }
            }
        }
    }

    fun saveJoke(joke: String) {
        viewModelScope.launch {
            cacheDataManager.addJoke(joke)
            updateJokesHistory()
        }
    }

    private suspend fun updateJokesHistory() {
        withContext(Dispatchers.IO) {
            val jokes = cacheDataManager.getJokesHistory()
            _jokeHistory.postValue(jokes)
        }
    }

    fun loadJokesHistory() {
        viewModelScope.launch {
            updateJokesHistory()
        }
    }

    fun selectJoke(joke : String)
    {
        selectedJoke.value = joke
        isConnectionError.value = false
    }

    fun deselectJoke()
    {
        selectedJoke.value = null
    }
}