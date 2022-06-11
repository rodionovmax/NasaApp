package com.example.nasa_app.util

import com.example.nasa_app.models.PictureModel
import com.example.nasa_app.db.entities.CurrentPODEntity
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

fun PictureModel.toCurrentPodEntity() : CurrentPODEntity {
    return CurrentPODEntity(
        id, date, url, title, explanation, copyright
    )
}

fun CurrentPODEntity.toPictureModel() : PictureModel {
    return PictureModel(
        id, date, url, title, explanation, copyright
    )
}
