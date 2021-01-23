package com.sscott.cemeterytrackerv1.data.models.entities.cemetery

import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave
import com.sscott.cemeterytrackerv1.data.models.mapper.DomainMapper

class CemeteryMapper : DomainMapper<Cemetery, CemeteryDomain>{
    override fun toDomainList(model: List<Cemetery>): List<CemeteryDomain> {
        return model.map {
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
                    graveCount = it.graveCount,
                    graves = null,
                    isSynced = it.isSynced
            )
        }
    }

    override suspend fun toDomain(model: Cemetery): CemeteryDomain {
        return CemeteryDomain(
                cemeteryId = model.cemeteryId,
                name = model.name,
                location = model.location,
                state = model.state,
                county = model.county,
                townShip = model.townShip,
                cemRange = model.cemRange,
                spot = model.spot,
                firstYear = model.firstYear,
                cemSection = model.cemSection,
                epochTimeAdded = model.epochTimeAdded,
                addedBy = model.addedBy,
                graveCount = model.graveCount,
                graves = null,
                isSynced = model.isSynced
        )
    }

    override suspend fun fromDomain(domainModel: CemeteryDomain): Cemetery {
        return Cemetery(
                cemeteryId = domainModel.cemeteryId,
                name = domainModel.name,
                location = domainModel.location,
                state = domainModel.state,
                county = domainModel.county,
                townShip = domainModel.townShip,
                cemRange = domainModel.cemRange,
                spot = domainModel.spot,
                firstYear = domainModel.firstYear,
                cemSection = domainModel.cemSection,
                epochTimeAdded = domainModel.epochTimeAdded,
                addedBy = domainModel.addedBy,
                graveCount = domainModel.graveCount,
                isSynced = domainModel.isSynced
        )
    }

    private fun listToDto(graveDomainList: List<GraveDomain>) : List<Grave> {
        return graveDomainList.map{
            Grave(
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
                    cemeteryId = it.cemeteryId,
                    isSynced = it.isSynced
            )
        }
    }

    fun toCemDomainList(cemGraves: List<CemeteryGraves>): List<CemeteryDomain> {
        return cemGraves.map {
           CemeteryDomain(
                   cemeteryId = it.cemetery.cemeteryId,
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
                   graves = toDomainGraveList(it.graves),
                   isSynced = it.cemetery.isSynced
           )
        }
    }

    private fun toDomainGraveList(graveDtoList : List<Grave>) : List<GraveDomain>{
        return graveDtoList.map {
            GraveDomain(
                    graveId = it.graveId,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    birthDate = it.birthDate,
                    deathDate = it.deathDate,
                    marriageYear = it.marriageYear,
                    comment = it.comment,
                    graveNumber = it.graveNumber,
                    epochTimeAdded = it.epochTimeAdded,
                    addedBy = it.addedBy ,
                    cemeteryId = it.cemeteryId,
                    isSynced = it.isSynced

            )
        }
    }
}