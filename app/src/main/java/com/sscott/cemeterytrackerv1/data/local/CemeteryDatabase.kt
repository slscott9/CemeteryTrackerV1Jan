package com.sscott.cemeterytrackerv1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sscott.cemeterytrackerv1.data.local.datasource.CemeteryDao
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave

@Database(entities = [Cemetery::class, Grave::class], version = 9)
abstract class CemeteryDatabase : RoomDatabase() {

    abstract fun cemeteryDao(): CemeteryDao

}