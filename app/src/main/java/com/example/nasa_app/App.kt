package com.example.nasa_app

import android.app.Application
import androidx.room.Room
import com.example.nasa_app.room.dao.PODDao
import com.example.nasa_app.room.db.MainDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var instance: MainDatabase? = null
        private const val MOVIE_DB_NAME = "main_database.db"
        fun getInstance(): PODDao {
            if (instance == null) {
                synchronized(MainDatabase::class.java) {
                    if (instance == null) {
                        if (appInstance == null) throw
                        IllegalStateException("Application is null while creating DataBase")
                        instance = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            MainDatabase::class.java,
                            MOVIE_DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return instance!!.favoritePodDao()
        }
    }
}
