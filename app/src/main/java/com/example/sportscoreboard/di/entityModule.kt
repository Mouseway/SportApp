package com.example.sportscoreboard.di

import com.example.sportscoreboard.data.local.room.AppDatabase
import com.example.sportscoreboard.data.local.room.adapters.SportObjectRoomAdapter
import com.example.sportscoreboard.data.remote.apiDescriptions.SportObjectApiDescription
import com.example.sportscoreboard.data.repositories.SportObjectsRepository
import com.example.sportscoreboard.screens.entitiesList.SportObjectsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val sportObjectModule = module {
    factory {
        get<Retrofit>().create(SportObjectApiDescription::class.java)
    }

    factory {
        get<AppDatabase>().sportObjectDao
    }

    factory {
        SportObjectsRepository(apiDescription = get(), dao = get(), entityAdapter = get())
    }

    viewModel {
        SportObjectsViewModel(repository = get<SportObjectsRepository>())
    }

    factory {
        SportObjectRoomAdapter()
    }
}