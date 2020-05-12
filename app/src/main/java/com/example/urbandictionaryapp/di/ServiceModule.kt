package com.example.urbandictionaryapp.di

import com.example.urbandictionaryapp.BuildConfig
import com.example.urbandictionaryapp.repository.remote.ServerAPI
import com.example.urbandictionaryapp.repository.remote.ServerRepository
import com.example.urbandictionaryapp.repository.remote.ServerRepositoryImpl
import org.koin.dsl.module

val serviceModule = module {
    single<ServerAPI> { createWebService(get(), BuildConfig.API_SERVER) }
    single<ServerRepository> { ServerRepositoryImpl(get()) }
}