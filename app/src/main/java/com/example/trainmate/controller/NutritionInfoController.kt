package com.example.trainmate.controller

import NutritionInfo
import android.content.ContentValues.TAG
import android.util.Log
import android.util.Log.*
import com.example.trainmate.api.NutritionAPI
import com.example.trainmate.entity.UserData
import com.example.trainmate.repository.NutritionInfoInterface
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class NutritionInfoController : NutritionInfoInterface {
    private val nutritionAPI = NutritionAPI()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun getAllNutritionInfo(userId: String): List<NutritionInfo>? {
        val snapshot = FirebaseFirestore
            .getInstance()
            .collection("meals")
            .whereEqualTo(FieldPath.documentId(), userId)
            .get()
            .await()

        val meals = mutableListOf<NutritionInfo>()

        for (document in snapshot.documents) {
            val meal = document.toObject(NutritionInfo::class.java)

            if (meal != null) {
                meals.add(meal)
            }
        }

        return meals
    }

    override suspend fun postNutritionInfo(userId: String, mealName: String) {
        try {
            val nutritionInfo = nutritionAPI.getNutritionInfo(mealName)

            if (nutritionInfo != null) {
                val userDocRef = FirebaseFirestore.getInstance().collection("users").document(userId)

                userDocRef.update("meals", FieldValue.arrayUnion(nutritionInfo))
                    .addOnSuccessListener {Log.d(TAG, "Meal added successfully.")
                    }
                    .addOnFailureListener { e -> Log.e(TAG, "Error updating meals list: $e") }
            } else {
                throw IllegalStateException("Nutrition info is null")
            }
        } catch (e:Exception) {
            Log.e(TAG, "TODO")
        }
    }
}