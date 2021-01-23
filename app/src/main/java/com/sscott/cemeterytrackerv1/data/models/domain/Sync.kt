package com.sscott.cemeterytrackerv1.data.models.domain

data class Sync(
    val mostRecentLocalInsert : Long?,
    val mostRecentServerInsert : Long?
)