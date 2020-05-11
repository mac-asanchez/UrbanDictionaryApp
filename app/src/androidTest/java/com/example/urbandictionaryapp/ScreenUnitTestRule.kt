package com.example.urbandictionaryapp

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.urbandictionaryapp.core.MyApp

class ScreenUnitTestRule<T : Activity>(
    activityClass: Class<T>,
    private val navigateToScreen: T.() -> Unit,
    private val setupMocks: () -> Unit = {}
) : ActivityTestRule<T>(activityClass) {
    @ExperimentalStdlibApi
    override fun beforeActivityLaunched() {
//        super.beforeActivityLaunched()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        (instrumentation.targetContext.applicationContext as MyApp).run {
            setupMocks()
        }
    }

    override fun afterActivityLaunched() {
        //super.afterActivityLaunched()
        activity.navigateToScreen()
    }
}