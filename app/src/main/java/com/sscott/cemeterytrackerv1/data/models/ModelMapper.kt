package com.sscott.cemeterytrackerv1.data.models

import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.CemeteryMapper
import com.sscott.cemeterytrackerv1.data.models.entities.grave.GraveMapper
import com.sscott.cemeterytrackerv1.data.models.mapper.DomainMapper
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDtoMapper
import com.sscott.cemeterytrackerv1.data.models.network.gravedto.GraveDtoMapper

class ModelMapperImpl : ModelMapperI{

    override val cem = CemeteryMapper()

    override val cemDto = CemeteryDtoMapper()

    override val graveDto = GraveDtoMapper()

    override val grave = GraveMapper()
}

interface ModelMapperI {
    val cemDto : CemeteryDtoMapper

    val cem : CemeteryMapper

    val graveDto : GraveDtoMapper

    val grave : GraveMapper
}