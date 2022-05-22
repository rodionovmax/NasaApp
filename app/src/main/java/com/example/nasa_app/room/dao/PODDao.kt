package com.example.nasa_app.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nasa_app.room.entities.FavoritePodEntity

@Dao
interface PODDao {
    @Query("SELECT * FROM FavoritePodEntity")
    fun getAllFavorites(): List<FavoritePodEntity>

    @Insert
    fun addPodToFavorites(entity: FavoritePodEntity)

    @Delete
    fun removePodToFavorites(entity: FavoritePodEntity)
}