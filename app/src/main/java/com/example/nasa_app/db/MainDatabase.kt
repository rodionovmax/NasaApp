package com.example.nasa_mars_api_service.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nasa_app.db.dao.NasaDao
import com.example.nasa_app.db.entities.PODEntity
import com.example.nasa_app.db.entities.FavoriteEntity

import com.example.nasa_mars_api_service.database.db.MainDatabase.Companion.DATABASE_VERSION

@Database(
    entities = [PODEntity::class, FavoriteEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class MainDatabase: RoomDatabase() {

    abstract val nasaDao: NasaDao

    companion object {
        const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "main_database.db"

        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getInstance(context: Context): MainDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MainDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}