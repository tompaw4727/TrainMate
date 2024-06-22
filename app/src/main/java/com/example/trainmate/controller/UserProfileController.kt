package com.example.trainmate.controller

import android.util.Log
import com.example.trainmate.entity.UserData
import com.example.trainmate.entity.toMap
import com.example.trainmate.repository.UserProfileInterface
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class UserProfileController : UserProfileInterface {
    private val db = Firebase.firestore
    override suspend fun getUserData(uidEmail: String): UserData? {
        return try {
            val userProfile = db.collection(uidEmail)
                .document("profile")
                .get()
                .await()

            userProfile.toObject(UserData::class.java)
        } catch (e: Exception) {
            Log.e("UserInfoController", "Exception in postUserData: ${e.message}")
            null
        }
    }

    override suspend fun postUserData(uidEmail: String, userData: UserData) {
        try {
            db.collection(uidEmail)
                .document("profile")
                .set(userData)
                .await()
        } catch (e: Exception) {
            Log.e("UserInfoController", "Exception in postUserData: ${e.message}")
        }
    }

    override suspend fun updateUserData(uidEmail: String, updatedUser: UserData) {
        try {
            val userDataMap = updatedUser.toMap()
            db.collection(uidEmail)
                .document("profile")
                .update(userDataMap)
                .await()
        } catch (e: Exception) {
            Log.e("UserInfoController", "Exception in updateUserData: ${e.message}")
        }
    }

    override suspend fun deleteUserData(userId: String) {
        TODO()
    }
}