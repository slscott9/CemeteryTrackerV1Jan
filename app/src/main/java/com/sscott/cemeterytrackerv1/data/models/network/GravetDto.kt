package com.sscott.cemeterytrackerv1.data.models.network

data class GravetDto(
    val graveId : Long?,
    val firstName : String?,
    val lastName : String?,
    val birthDate : String?,
    val deathDate : String?,
    val marriageYear : String?,
    val comment : String?,
    val graveNumber : String?,
    val epochTimeAdded : Long?,
    val addedBy : String?,
    val cemetery : Long?

)