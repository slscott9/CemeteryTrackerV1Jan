package com.sscott.cemeterytrackerv1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sscott.cemeterytrackerv1.data.local.datasource.CemeteryDao
import com.sscott.cemeterytrackerv1.data.models.entities.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.Grave

@Database(entities = [Cemetery::class, Grave::class], version = 11)
abstract class CemeteryDatabase : RoomDatabase() {

    abstract fun cemeteryDao(): CemeteryDao

}