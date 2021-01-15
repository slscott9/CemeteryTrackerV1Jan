package com.sscott.cemeterytrackerv1.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cemetery(
    @PrimaryKey
    val cemeteryId : Long,
    val name : String,
    val location : String,
    val state : String,
    val county : String,
    val townShip : String,
    val cemRange : String,
    val spot : String,
    val firstYear : String,
    val cemSection : String,
    val epochTimeAdded : Long,
    val addedBy : String,
    val graveCount : Int
)

