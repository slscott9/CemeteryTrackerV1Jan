package com.sscott.cemeterytrackerv1.data.models.network

data class GravetDto(
    val graveId : String,
    val firstName : String,
    val lastName : String,
    val birthDate : String,
    val deathDate : String,
    val marriageYear : String,
    val comment : String,
    val graveNumber : String,
    val epochTimeAdded : String,
    val addedBy : String,
    val cemetery : Long

)