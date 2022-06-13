package com.example.nasa_app.room.dao

import androidx.room.*
import com.example.nasa_app.room.entities.CurrentPODEntity
import com.example.nasa_app.room.entities.FavoritesEntity

@Dao
interface PODDao {
    @Query("SELECT * FROM FavoritesEntity")
    fun getAllFavorites(): List<FavoritesEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPODToFavorites(entity: FavoritesEntity)

    @Delete
    fun removePODToFavorites(entity: FavoritesEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCurrentPOD(entity: CurrentPODEntity)

    @Query("SELECT * FROM CurrentPODEntity WHERE id = (SELECT max(id) FROM CurrentPODEntity)")
    fun getCurrentPOD() : CurrentPODEntity
}
