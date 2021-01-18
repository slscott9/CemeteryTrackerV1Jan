package com.sscott.cemeterytrackerv1.data.models.network

import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.entities.Cemetery

data class CemeteryDto(
    val cemeteryId : Long?,
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
    val graves : List<GravetDto>?
)

/*
    NEED TO THINK OF A CACHING STRATEGY MAYBE SAVE LOCALLY AND GIVE THE OPTION TO REFRESH WITH SWIPE
 */

/*
    If converting to a domain model a cemeteryId will always be available
 */
fun List<CemeteryDto>.asDomainModels() : List<CemeteryDomain> {
    return map {
        CemeteryDomain(
            cemeteryId = it.cemeteryId!!,
            name = it.name!!,
            location = it.location ?: "",
            state = it.state ?: "",
            county = it.county ?: "",
            townShip = it.townShip ?: "",
            cemRange = it.cemRange ?: "",
            spot = it.spot ?: "",
            firstYear = it.firstYear ?: "",
            cemSection = it.cemSection ?: "",
            epochTimeAdded = it.epochTimeAdded!!,
            addedBy = it.addedBy ?: "",
            graveCount = it.graveCount ?: 0
        )
    }
}

fun List<CemeteryDto>.asDatabaseModels() : List<Cemetery> {
    return map {
        Cemetery(
            cemeteryId = it.cemeteryId ?: 0,
            name = it.name!!,
            location = it.location ?: "",
            state = it.state ?: "",
            county = it.county ?: "",
            townShip = it.townShip ?: "",
            cemRange = it.cemRange ?: "",
            spot = it.spot ?: "",
            firstYear = it.firstYear ?: "",
            cemSection = it.cemSection ?: "",
            epochTimeAdded = it.epochTimeAdded!!,
            addedBy = it.addedBy ?: "",
            graveCount = it.graveCount ?: 0
        )
    }
}

/*
    If inserting into db then user does not have network connection
    Let room generate a cemeteryId
 */
fun CemeteryDto.asDatabaseModel() : Cemetery {
       return Cemetery(
           cemeteryId = cemeteryId ?: 0,
           name = name!!,
           location = location ?: "",
           state = state ?: "",
           county = county ?: "",
           townShip = townShip ?: "",
           cemRange = cemRange ?: "",
           spot = spot ?: "",
           firstYear = firstYear ?: "",
           cemSection = cemSection ?: "",
           epochTimeAdded = epochTimeAdded!!,
           addedBy = addedBy ?: "",
           graveCount = graveCount ?: 0
        )

}