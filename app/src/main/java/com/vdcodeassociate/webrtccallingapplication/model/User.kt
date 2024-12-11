package com.vdcodeassociate.webrtccallingapplication.model

data class User(
    val name: String = "",
    val latestEvent: LatestEvent = LatestEvent(),
    val status: String = "OFFLINE"
)