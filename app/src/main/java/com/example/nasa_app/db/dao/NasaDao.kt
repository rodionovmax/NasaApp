package com.example.nasa_app.db.dao

import androidx.room.*
import com.example.nasa_app.db.entities.PODEntity
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
    fun saveCurrentPOD(entity: PODEntity)

    @Query("SELECT * FROM PODEntity WHERE id = (SELECT max(id) FROM PODEntity)")
    fun getCurrentPOD() : PODEntity
}
