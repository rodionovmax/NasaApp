package com.example.nasa_app.util

import com.example.nasa_app.models.PictureModel
import com.example.nasa_app.db.entities.PODEntity
import com.example.nasa_app.db.entities.FavoriteEntity

fun FavoriteEntity.toPictureModel() : PictureModel {
    return PictureModel(
        id, date, url, title, explanation, copyright
    )
}

fun PictureModel.toFavoriteEntity() : FavoriteEntity {
    return FavoriteEntity(
        id, date, url, title, explanation, copyright
    )
}

fun PictureModel.toCurrentPodEntity() : PODEntity {
    return PODEntity(
        id, date, url, title, explanation, copyright
    )
}

fun PODEntity.toPictureModel() : PictureModel {
    return PictureModel(
        id, date, url, title, explanation, copyright
    )
}
