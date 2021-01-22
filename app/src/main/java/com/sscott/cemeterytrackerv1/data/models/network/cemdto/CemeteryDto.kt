package com.sscott.cemeterytrackerv1.data.models.network.cemdto



import com.sscott.cemeterytrackerv1.data.models.network.gravedto.GraveDto

/*
    Server sends grave list back. domain has a graves list as well
 */

data class CemeteryDto(
    val id : Long?,
    val name : String?,
    val location : String?,
    val state : String?,
    val county : String?,
    val townShip : String?,
    val cemRange : String?,
    val spot : String?,
    val firstYear : String?,
    val cemSection : String?,
    val epochTimeAdded : Long?,
    val addedBy : String?,
    val graveCount : Int?,
    val graves : List<GraveDto>?
)

