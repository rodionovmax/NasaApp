package com.example.nasa_app.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val date: String,
    val url: String,
    val title: String,
    val explanation: String,
    val copyright: String
)
