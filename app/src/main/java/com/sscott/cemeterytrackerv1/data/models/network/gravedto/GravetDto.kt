package com.sscott.cemeterytrackerv1.data.models.network.gravedto

import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain

data class GraveDto(
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
    val cemId : Long?

)



