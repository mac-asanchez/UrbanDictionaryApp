package com.example.urbandictionaryapp.di

import com.example.urbandictionaryapp.presentation.navigation.Navigation
import com.example.urbandictionaryapp.presentation.navigation.NavigationImpl
import org.koin.dsl.module

val myAppModule = module {
    single<Navigation> { NavigationImpl() }
}