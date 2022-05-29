package com.example.nasa_app.room.dao

import androidx.room.*
import com.example.nasa_app.room.entities.CurrentPODEntity
import com.example.nasa_app.room.entities.PODEntity

@Dao
interface PODDao {
    @Query("SELECT * FROM PODEntity")
    fun getAllFavorites(): List<PODEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPODToFavorites(entity: PODEntity)

    @Delete
    fun removePODToFavorites(entity: PODEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCurrentPOD(entity: CurrentPODEntity)

    @Query("SELECT * FROM CurrentPODEntity WHERE id = (SELECT max(id) FROM CurrentPODEntity)")
    fun getCurrentPOD() : CurrentPODEntity
}
