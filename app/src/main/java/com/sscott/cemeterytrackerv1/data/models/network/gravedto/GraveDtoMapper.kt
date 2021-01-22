package com.sscott.cemeterytrackerv1.data.models.network.gravedto

import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.data.models.mapper.DomainMapper

class GraveDtoMapper : DomainMapper<GraveDto, GraveDomain> {
    override suspend fun toDomainList(model: List<GraveDto>): List<GraveDomain> {
        return model.map {
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

    override suspend fun toDomain(model: GraveDto): GraveDomain {
        return GraveDomain(
                graveId = model.graveId!!,
                firstName = model.firstName ?: "",
                lastName = model.lastName ?: "",
                birthDate = model.birthDate ?: "",
                deathDate = model.deathDate ?: "",
                marriageYear = model.marriageYear ?: "",
                comment = model.comment ?: "",
                graveNumber = model.graveNumber ?: "",
                epochTimeAdded = model.epochTimeAdded!!,
                addedBy = model.addedBy ?: "",
                cemeteryId = model.cemId!!
        )
    }

    override suspend fun fromDomain(domainModel: GraveDomain): GraveDto {
        return GraveDto(
                graveId = domainModel.graveId,
                firstName = domainModel.firstName,
                lastName = domainModel.lastName,
                birthDate = domainModel.birthDate,
                deathDate = domainModel.deathDate,
                marriageYear = domainModel.marriageYear,
                comment = domainModel.comment,
                graveNumber = domainModel.graveNumber,
                epochTimeAdded = domainModel.epochTimeAdded,
                addedBy = domainModel.addedBy,
            cemId = domainModel.cemeteryId
        )
    }
}