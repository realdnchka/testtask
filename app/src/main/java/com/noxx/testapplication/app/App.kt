package com.noxx.testapplication.app

import android.app.Application
import org.greenrobot.eventbus.EventBus
import com.noxx.testapplication.eventbus.MkbEventBusIndex


open class App : Application() {
    override fun onCreate() {
        EventBus.builder().addIndex(MkbEventBusIndex()).installDefaultEventBus()
        super.onCreate()
    }
}