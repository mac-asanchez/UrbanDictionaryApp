package com.example.urbandictionaryapp

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

//Resource for waiting
class DelayableIdlingResource {

    private val counter: CountingIdlingResource = CountingIdlingResource("DelayableIdlingResource${hashCode()}").apply {
        IdlingRegistry.getInstance().register(this)
    }

    fun wait(timeMillis: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) {
        counter.increment()
        GlobalScope.launch {
            delay(timeMillis)
            counter.decrement()
        }
    }

}