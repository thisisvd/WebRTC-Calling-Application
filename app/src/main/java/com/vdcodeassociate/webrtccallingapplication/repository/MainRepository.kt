package com.example.webrtccallingapplication.repository

import com.vdcodeassociate.webrtccallingapplication.firebaseClient.FirebaseClient
import com.vdcodeassociate.webrtccallingapplication.model.User
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    fun login(username : String, password : String, done : (Boolean, String?) -> Unit) {
        firebaseClient.login(username, password, done)
    }

    fun observeUserStates(status : (List<User>) -> Unit) {
        firebaseClient.observeUserStates(status)
    }
}