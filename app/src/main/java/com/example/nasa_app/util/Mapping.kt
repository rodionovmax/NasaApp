package com.example.nasa_app.util

import com.example.nasa_app.network.models.PODModel
import com.example.nasa_app.room.entities.CurrentPODEntity
import com.example.nasa_app.room.entities.PODEntity

fun PODEntity.toPodModel() : PODModel {
    return PODModel(
        id, date, url, title, explanation, copyright
    )
}

fun PODModel.toPodEntity() : PODEntity {
    return PODEntity(
        id, date, url, title, explanation, copyright
    )
}

fun PODModel.toCurrentPodEntity() : CurrentPODEntity {
    return CurrentPODEntity(
        id, date, url, title, explanation, copyright
    )
}

fun CurrentPODEntity.toPodModel() : PODModel {
    return PODModel(
        id, date, url, title, explanation, copyright
    )
}
