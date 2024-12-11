package com.vdcodeassociate.webrtccallingapplication.model

data class UsersData(
    val firebaseUsers: Map<String, User> = emptyMap()
)