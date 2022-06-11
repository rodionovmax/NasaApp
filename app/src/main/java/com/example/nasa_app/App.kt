package com.example.nasa_app

import android.app.Application
import com.example.nasa_mars_api_service.database.db.MainDatabase


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private lateinit var appInstance: App
        fun getDB() = MainDatabase.getInstance(appInstance.applicationContext)
    }
}
