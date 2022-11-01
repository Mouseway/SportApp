package com.example.sportscoreboard.di

import androidx.room.Room
import com.example.sportscoreboard.data.local.room.AppDatabase
import com.example.sportscoreboard.data.remote.adapters.SportObjectJsonAdapter
import com.example.sportscoreboard.domain.SportObject
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    single {
        Moshi.Builder()
            .add(SportObjectJsonAdapter())
            .build()
    }

    factory {
        get<Moshi>().adapter(SportObject::class.java).lenient()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://s.livesport.services/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "entities_db"
        )
            .build()
    }
}