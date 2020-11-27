package com.noxx.testapplication

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import com.noxx.testapplication.ui.MainActivity
import junit.framework.Assert.assertEquals
import org.hamcrest.Matchers.notNullValue
import org.junit.*
import org.junit.Assert.assertThat
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

private const val BASIC_SAMPLE_PACKAGE = "com.noxx.testapplication"
private const val LAUNCH_TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ExampleInstrumentedTest {
    private val device: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private lateinit var loginActivity: LoginActivity

    @Before
    fun startActivity() {
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

        device.wait(
            Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
            LAUNCH_TIMEOUT
        )
    }

    @Test
    fun loginAWithoutPassword() {
        loginActivity = LoginActivity(device)

        loginActivity.setLoginField()
        loginActivity.clickLoginButton()

        val errorMessage = loginActivity.getErrorText()
        assertEquals("Необходимо ввести пароль", errorMessage)
        loginActivity.clickErrorButton()
    }

    @Test
    fun loginBIncorrectPassword() {
        loginActivity = LoginActivity(device)

        loginActivity.setLoginField()
        loginActivity.setPasswordField("1")
        loginActivity.clickLoginButton()

        val errorMessage = loginActivity.getErrorText()
        assertEquals("Логин или пароль введен неверно", errorMessage)
        loginActivity.clickErrorButton()
    }

    @Test
    fun loginCCorrect() {
        loginActivity = LoginActivity(device)

        loginActivity.setLoginField()
        loginActivity.setPasswordField("autotest")
        loginActivity.clickLoginButton()

        val errorMessage = loginActivity.getErrorText()
        assertEquals("Успешный вход в приложение", errorMessage)
        loginActivity.clickErrorButton()
    }
}
