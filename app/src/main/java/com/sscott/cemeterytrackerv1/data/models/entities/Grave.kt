package com.sscott.cemeterytrackerv1.data.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*
    Cemetery has many graves

    Autogenerate primary keys since an insert of a grave means user does not have internet access.

    Grave needs to be sent to network through work manager
 */

@Entity(tableName = "grave_v1_table", foreignKeys = arrayOf(
    ForeignKey(entity = Cemetery::class,
        parentColumns = arrayOf("cemeteryId"),
        childColumns = arrayOf("cemeteryId"),
        onDelete = ForeignKey.CASCADE

    )
))
data class Grave(
    @PrimaryKey(autoGenerate = true)
    val graveId : Long = 0,
    val cemeteryId: Long,
    val firstName: String,
    val lastName : String,
    val birthDate : String,
    val deathDate : String,
    val marriageYear : String,
    val comment : String,
    val graveNumber : String,
    val epochTimeAdded : Long, //exclude when sending to server so server can add its own epoch time added
    val addedBy : String,


)

