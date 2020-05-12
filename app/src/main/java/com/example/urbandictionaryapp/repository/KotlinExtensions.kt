package com.example.urbandictionaryapp.repository

import kotlinx.coroutines.Deferred

suspend fun <T, R> Deferred<T>.runOnResult(callback: T.() -> R): R = callback(this.await())