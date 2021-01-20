package com.sscott.cemeterytrackerv1.data.models.network


import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.entities.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.CemeteryGraves
import com.sscott.cemeterytrackerv1.data.models.entities.Grave


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
    val graves : List<GravetDto>?
)

/*
    Cemetery/Grave ids will be generated on client side to be consitent
    Server needs Cemeterys to have an id in order to insert a list of cemeteries with a list of graves
 */

fun List<CemeteryGraves>.asNetworkModelList() : List<CemeteryDto>{
    return map {
        CemeteryDto(
            id = it.cemetery.cemeteryId,
            name = it.cemetery.name,
            location = it.cemetery.location,
            state = it.cemetery.state,
            county = it.cemetery.county,
            townShip = it.cemetery.townShip,
            cemRange = it.cemetery.cemRange,
            spot = it.cemetery.spot,
            firstYear = it.cemetery.firstYear,
            cemSection = it.cemetery.cemSection,
            epochTimeAdded = it.cemetery.epochTimeAdded,
            addedBy = it.cemetery.addedBy,
            graveCount = it.cemetery.graveCount,
            graves = it.graves.asNetworkModels()
        )
    }
}

fun List<Grave>.asNetworkModels() : List<GravetDto>{
    return map {
        GravetDto(
            graveId = it.graveId,
            firstName = it.firstName,
            lastName = it.lastName,
            birthDate = it.birthDate,
            deathDate = it.deathDate,
            marriageYear = it.marriageYear,
            comment = it.comment,
            graveNumber = it.graveNumber,
            epochTimeAdded = it.epochTimeAdded,
            addedBy = it.addedBy,
            cemetery = it.cemeteryId
        )
    }
}

/*
    If converting to a domain model a cemeteryId will always be available
 */
fun List<CemeteryDto>.asDomainModels() : List<CemeteryDomain> {
    return map {
        CemeteryDomain(
            cemeteryId = it.id!!,
            name = it.name ?: "",
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
            cemeteryId = it.id ?: 0,
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
        cemeteryId = id ?: 0,
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