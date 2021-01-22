package com.sscott.cemeterytrackerv1.data.models.network.cemdto

import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.data.models.mapper.DomainMapper
import com.sscott.cemeterytrackerv1.data.models.network.gravedto.GraveDto

class CemeteryDtoMapper : DomainMapper<CemeteryDto, CemeteryDomain> {
    override fun toDomainList(model: List<CemeteryDto>): List<CemeteryDomain> {
        return model.map {
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
                    graveCount = it.graveCount ?: 0,
                    graves = graveDtoListToDomain(it.graves ?: emptyList())
            )
        }
    }

    override suspend fun toDomain(model: CemeteryDto): CemeteryDomain {
        return CemeteryDomain(
                cemeteryId = model.id!!,
                name = model.name ?: "",
                location = model.location ?: "",
                state = model.state ?: "",
                county = model.county ?: "",
                townShip = model.townShip ?: "",
                cemRange = model.cemRange ?: "",
                spot = model.spot ?: "",
                firstYear = model.firstYear ?: "",
                cemSection = model.cemSection ?: "",
                epochTimeAdded = model.epochTimeAdded!!,
                addedBy = model.addedBy ?: "",
                graveCount = model.graveCount ?: 0,
                graves = graveDtoListToDomain(model.graves ?: emptyList())
        )
    }

    override suspend fun fromDomain(domainModel: CemeteryDomain): CemeteryDto {
        return CemeteryDto(
                id = domainModel.cemeteryId,
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
                graves = graveDomainListToDto(domainModel.graves ?: emptyList())
        )
    }

    private fun graveDomainListToDto(graveDomainList: List<GraveDomain>) : List<GraveDto> {
        return graveDomainList.map{
            GraveDto(
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
                    cemId = it.cemeteryId
            )
        }
    }

    fun toNetworkList(cemDomainList : List<CemeteryDomain>) : List<CemeteryDto>{
        return cemDomainList.map{
            CemeteryDto(
                    id = it.cemeteryId,
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
                    graves = graveDomainListToDto(it.graves ?: emptyList())
            )
        }
    }

    private fun graveDtoListToDomain(graveDtoList : List<GraveDto>) : List<GraveDomain>{
        return graveDtoList.map {
            GraveDomain(
                    graveId = it.graveId!!,
                    firstName = it.firstName ?: "",
                    lastName = it.lastName ?: "",
                    birthDate = it.birthDate ?: "",
                    deathDate = it.deathDate ?: "",
                    marriageYear = it.marriageYear ?: "",
                    comment = it.comment ?: "",
                    graveNumber = it.graveNumber ?: "",
                    epochTimeAdded = it.epochTimeAdded!!,
                    addedBy = it.addedBy ?: "",
                    cemeteryId = it.cemId!!

            )
        }
    }
}