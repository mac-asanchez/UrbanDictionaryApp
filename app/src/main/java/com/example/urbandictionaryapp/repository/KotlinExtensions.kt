package com.example.urbandictionaryapp.repository

import kotlinx.coroutines.Deferred

//Suspended function that awaits the result of a coroutine
suspend fun <T, R> Deferred<T>.runOnResult(callback: T.() -> R): R = callback(this.await())