package com.noxx.testapplication

import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector

class LoginActivity(private val device: UiDevice) {
    private val loginText: String = "autotest"

    fun setLoginField() {
        val loginField: UiObject = device.findObject(UiSelector().text("Логин").className("android.widget.EditText"))
        loginField.setText(loginText)
    }

    fun setPasswordField(password: String) {
        val passwordField: UiObject = device.findObject(UiSelector().text("Пароль").className("android.widget.EditText"))
        passwordField.setText(password)
    }

    fun clickLoginButton() {
        val loginButton: UiObject = device.findObject(UiSelector().text("ВОЙТИ").className("android.widget.Button"))
        loginButton.click()
    }

    fun getErrorText(): String {
        val errorMessage: UiObject = device.findObject(UiSelector().resourceId("com.noxx.testapplication:id/message"))
        return errorMessage.text.toString()
    }

    fun clickErrorButton() {
        val errorButton: UiObject = device.findObject(UiSelector().text("ОК").resourceId("com.noxx.testapplication:id/positive"))
        errorButton.click()
    }
}