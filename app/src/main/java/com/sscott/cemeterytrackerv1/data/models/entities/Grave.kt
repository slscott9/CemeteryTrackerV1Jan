package com.sscott.cemeterytrackerv1.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Grave(
    @PrimaryKey
    val graveId : Long,
    val firstName: String,
    val lastName : String,
    val birthDate : String,
    val deathDate : String,
    val marriageYear : String,
    val comment : String,
    val graveNumber : String,
    val epochTimeAdded : Long,
    val addedBy : String,

)