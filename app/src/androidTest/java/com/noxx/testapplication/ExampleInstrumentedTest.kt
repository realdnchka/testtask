package com.noxx.testapplication

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.Matchers.notNullValue
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

private const val BASIC_SAMPLE_PACKAGE = "com.noxx.testapplication"
private const val LAUNCH_TIMEOUT = 5000L
private const val STRING_TO_BE_TYPED = "UiAutomator"

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var device: UiDevice
    @Test
    fun startActivity() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        device.pressHome()

        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT
        )

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(
            BASIC_SAMPLE_PACKAGE).apply {
            // Clear out any previous instances
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
    }
}
