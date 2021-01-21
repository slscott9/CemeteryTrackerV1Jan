package com.sscott.cemeterytrackerv1.data.models

import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.CemeteryMapper
import com.sscott.cemeterytrackerv1.data.models.entities.grave.GraveMapper
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDtoMapper
import com.sscott.cemeterytrackerv1.data.models.network.gravedto.GraveDtoMapper

abstract class ModelMapper {

    val cemeteryMapper = CemeteryMapper()

    val cemeteryDtoMapper = CemeteryDtoMapper()

    val graveDtoMapper = GraveDtoMapper()

    val graveMapper = GraveMapper()
}