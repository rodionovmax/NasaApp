package com.example.nasa_app.db.dao

import androidx.room.*
import com.example.nasa_app.db.entities.CurrentPODEntity
import com.example.nasa_app.db.entities.FavoriteEntity

@Dao
interface NasaDao {
    @Query("SELECT * FROM FavoriteEntity")
    fun getAllFavorites(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPODToFavorites(entity: FavoriteEntity)

    @Delete
    fun removePODToFavorites(entity: FavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCurrentPOD(entity: CurrentPODEntity)

    @Query("SELECT * FROM CurrentPODEntity WHERE id = (SELECT max(id) FROM CurrentPODEntity)")
    fun getCurrentPOD() : CurrentPODEntity
}
