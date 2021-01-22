package com.sscott.cemeterytrackerv1.data.models.mapper

interface DomainMapper<T, DomainModel> {
    fun toDomainList(model : List<T>) : List<DomainModel>

    suspend fun fromDomain(domainModel: DomainModel) : T

    suspend fun toDomain(model: T) : DomainModel

}