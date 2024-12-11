package com.vdcodeassociate.webrtccallingapplication.repository

import com.vdcodeassociate.webrtccallingapplication.firebaseClient.FirebaseClient
import com.vdcodeassociate.webrtccallingapplication.model.User
import com.vdcodeassociate.webrtccallingapplication.prefdata.PreferenceImpl
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val firebaseClient: FirebaseClient,
    private val preferenceImpl: PreferenceImpl
) {

    fun login(username: String, password: String, done: (Boolean, String?) -> Unit) {
        firebaseClient.login(username, password, done)
    }

    fun logout(username: String, done: (Boolean, String?) -> Unit) {
        firebaseClient.logout(username, done)
    }

    fun observeUserStates(status: (List<User>) -> Unit) {
        firebaseClient.observeUserStates(status)
    }

    fun getLoggedUsername() = preferenceImpl.getLoggedUsername()
}