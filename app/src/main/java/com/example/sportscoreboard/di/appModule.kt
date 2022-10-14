package com.example.sportscoreboard.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://s.livesport.services/api/v2/search")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}