package com.sscott.cemeterytrackerv1.data.models.network

data class UserDto(
    val userName : String,
    val email: String,
    val password : String,
    val gravesAdded: Int?,
    val cemeteriesAdded: Int?
) {
}