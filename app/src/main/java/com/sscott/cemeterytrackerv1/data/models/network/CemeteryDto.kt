package com.sscott.cemeterytrackerv1.data.models.network

import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain

data class CemeteryDto(
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


fun List<CemeteryDto>.asDomainModels() : List<CemeteryDomain> {
    return map {
        CemeteryDomain(
            cemeteryId = it.cemeteryId,
            name = it.name,
            location = it.location,
            state = it.state,
            county = it.county,
            townShip = it.townShip,
            cemRange = it.cemRange,
            spot = it.spot,
            firstYear = it.firstYear,
            cemSection = it.cemSection,
            epochTimeAdded = it.epochTimeAdded,
            addedBy = it.addedBy,
            graveCount = it.graveCount
        )
    }
}