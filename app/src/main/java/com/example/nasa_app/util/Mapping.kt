package com.example.nasa_app.util

import com.example.nasa_app.network.models.PODModel
import com.example.nasa_app.room.entities.FavoritePodEntity

fun FavoritePodEntity.toPodModel() : PODModel {
    return PODModel(
        id, date, url, title, explanation, copyright
    )
}

fun PODModel.toPodEntity() : FavoritePodEntity {
    return FavoritePodEntity(
        id, date, url, title, explanation, copyright
    )
}