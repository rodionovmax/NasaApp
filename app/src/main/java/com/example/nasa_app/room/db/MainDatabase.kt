package com.example.nasa_app.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nasa_app.room.dao.PODDao
import com.example.nasa_app.room.entities.FavoritePodEntity

@Database(entities = [FavoritePodEntity::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {
    abstract fun favoritePodDao(): PODDao

//    companion object {
//        const val DATABASE_VERSION = 1
//        const val DATABASE_NAME = "main_database.db"
//
//        @Volatile
//        private var INSTANCE: MainDatabase? = null
//
//        fun getInstance(context: Context): MainDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        MainDatabase::class.java,
//                        DATABASE_NAME
//                    )
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//
//                return instance
//            }
//        }
//    }
}