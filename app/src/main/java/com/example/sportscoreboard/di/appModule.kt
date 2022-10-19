package com.example.sportscoreboard.di

import com.example.sportscoreboard.data.remote.adapters.ParticipantAdapter
import com.example.sportscoreboard.data.remote.apiDescriptions.ParticipantsApiDescription
import com.example.sportscoreboard.data.repositories.ParticipantsRepository
import com.example.sportscoreboard.screens.scoreboard.ParticipantsViewModel
import com.squareup.moshi.Moshi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    single {
        Moshi.Builder()
            .add(ParticipantAdapter())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://s.livesport.services/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    factory {
        get<Retrofit>().create(ParticipantsApiDescription::class.java)
    }

    factory {
        ParticipantsRepository(apiDescription = get())
    }

    viewModel {
        ParticipantsViewModel(repository = get())
    }
}