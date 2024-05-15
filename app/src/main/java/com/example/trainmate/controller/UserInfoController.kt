package com.example.trainmate.controller

import NutritionInfo
import com.example.trainmate.api.NutritionAPI
import com.example.trainmate.entity.UserData
import com.example.trainmate.repository.UserInfoInterface
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserInfoController : UserInfoInterface {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun getUserData(userId: String): UserData? {
        val snapshot = FirebaseFirestore
            .getInstance()
            .collection("users")
            .whereEqualTo(FieldPath.documentId(), userId)
            .get()
            .await()

        return snapshot.documents.firstOrNull()?.toObject(UserData::class.java)
    }

    override suspend fun postUserData(userId: String, userData: UserData) {
        try {
            firestore.collection("users").document(userId).set(userData).await()
        } catch (e:Exception) {
            TODO()
        }
    }

    override suspend fun updateUserData(userId: String, updatedUser: UserData) {
        try {
            firestore.collection("users").document(userId).set(updatedUser).await()
        } catch (e: Exception) {
            TODO()
        }
    }

    override suspend fun deleteUserData(userId: String) {
        try {
            firestore.collection("users").document(userId).delete().await()
        } catch (e: Exception) {
            TODO()
        }
    }
}