package com.example.chucknorrisjokesapp.cache

import android.content.SharedPreferences

class CacheDataManager(private val sharedPreferences: SharedPreferences) {

    private companion object {
        const val JOKE_KEY_PREFIX = "joke_"
        const val MAX_JOKES = 3
    }

    fun addJoke(joke: String) {
        val jokes = mutableListOf<String>()

        for (i in 0 until MAX_JOKES)
        {
            val existingJoke = sharedPreferences.getString("$JOKE_KEY_PREFIX$i", null)
            existingJoke?.let { jokes.add(it) }
        }

        if (jokes.size >= MAX_JOKES)
        {
            jokes.removeAt(0)
        }
        jokes.add(joke)

        for (i in jokes.indices)
        {
            sharedPreferences.edit().putString("$JOKE_KEY_PREFIX$i", jokes[i]).apply()
        }
    }

    fun getJokesHistory(): List<String>
    {
        val jokes = mutableListOf<String>()

        for (i in 0 until MAX_JOKES)
        {
            val existingJoke = sharedPreferences.getString("$JOKE_KEY_PREFIX$i", null)
            existingJoke?.let { jokes.add(it) }
        }

        return jokes
    }
}
