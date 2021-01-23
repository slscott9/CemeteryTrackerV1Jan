package com.sscott.cemeterytrackerv1.data.models.entities.cemetery

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave

@Entity(tableName = "cemetery_v1_table")
data class Cemetery(
    @PrimaryKey
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
    val graveCount : Int,
    val isSynced: Boolean
)

data class CemeteryGraves(
        @Embedded val cemetery: Cemetery,
        @Relation(
        parentColumn = "cemeteryId",
        entityColumn = "cemeteryId",
    )
    val graves: List<Grave>
)

