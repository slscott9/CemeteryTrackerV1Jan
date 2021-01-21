package com.sscott.cemeterytrackerv1.data.models.mapper

interface DomainMapper<T, DomainModel> {
    suspend fun mapToDomainModelList(model : List<T>) : List<DomainModel>

    suspend fun mapFromDomainModel(domainModel: DomainModel) : T

    suspend fun mapToDomainModel(model: T) : DomainModel

}