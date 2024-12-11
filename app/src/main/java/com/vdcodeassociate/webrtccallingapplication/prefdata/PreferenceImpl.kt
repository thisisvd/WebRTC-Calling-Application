package com.vdcodeassociate.webrtccallingapplication.prefdata

import android.content.SharedPreferences
import com.vdcodeassociate.webrtccallingapplication.utils.Constants.FIREBASE_USERNAME
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun saveUserLogin(userName: String) {
        sharedPreferences.edit().apply {
            putString(FIREBASE_USERNAME, userName)
        }.apply()
    }

    fun getLoggedUsername(): String {
        return sharedPreferences.getString(FIREBASE_USERNAME, "").toString()
    }
}