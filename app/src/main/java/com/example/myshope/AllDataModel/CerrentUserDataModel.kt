package com.example.myshope.AllDataModel

data class CurrentUserDataModel(
    val userId: String? = null, // User ID
    val email: String? = null,  // User email
    val password: String? = null // User password (hashed, ideally)
)