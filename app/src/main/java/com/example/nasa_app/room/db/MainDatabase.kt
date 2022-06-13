package com.example.nasa_app.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nasa_app.room.dao.PODDao
import com.example.nasa_app.room.entities.CurrentPODEntity
import com.example.nasa_app.room.entities.FavoritesEntity

@Database(entities = [FavoritesEntity::class, CurrentPODEntity::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {
    abstract fun favoritePodDao(): PODDao
}
