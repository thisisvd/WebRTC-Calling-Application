package com.vdcodeassociate.webrtccallingapplication.firebaseClient

import android.util.Log
import com.example.webrtccallingapplication.utils.Constants
import com.example.webrtccallingapplication.utils.UserStatus
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.vdcodeassociate.webrtccallingapplication.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor(
    private val dbRef : DatabaseReference,
    private val gson : Gson
) {

    private var currentUserName : String? = null
    private fun setUserName(username: String) {
        this.currentUserName = username
    }

    fun login(username: String, password: String, done: (Boolean, String?) -> Unit) {
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(username)) {
                    // user exists
                    val dbPassword = snapshot.child(username).child(Constants.PASSWORD).value
                    if (password == dbPassword) {
                        dbRef.child(username).child(Constants.STATUS).setValue(UserStatus.ONLINE)
                            .addOnCompleteListener {
                                done(true, null)
                                setUserName(username)
                            }.addOnFailureListener {
                                Log.d("FB_TAG", "Exception : ${it.message}")
                                done(false, it.message.toString())
                            }
                    } else {
                        done(false, "Wrong Password!")
                    }
                } else {
                    // user doesn't exists
                    dbRef.child(username).child(Constants.PASSWORD).setValue(password)
                        .addOnCompleteListener {
                            dbRef.child(username).child(Constants.STATUS)
                                .setValue(UserStatus.ONLINE).addOnCompleteListener {
                                    done(true, null)
                                    setUserName(username)
                                }.addOnFailureListener {
                                    done(false, it.message)
                                }
                        }.addOnFailureListener {
                            done(false, it.message)
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                done(false, error.message)
            }
        })
    }

    fun observeUserStates(status : (List<User>) -> Unit) {
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val usersMapType = object : GenericTypeIndicator<Map<String, Map<String, String>>>() {}
                val usersMap = snapshot.getValue(usersMapType)

                if (usersMap != null) {
                    // Convert the map of users to a list of User objects
                    val userList = usersMap.map { entry ->
                        val username = entry.key
                        val userData = entry.value
                        User(
                            name = username ?: "",
                            status = userData["status"] ?: ""
                        )
                    }

                    status(userList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}