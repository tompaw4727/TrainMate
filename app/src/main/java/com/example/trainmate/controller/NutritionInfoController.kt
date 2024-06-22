package com.example.trainmate.controller

import android.util.Log
import com.example.trainmate.entity.NutritionInfo
import com.example.trainmate.repository.NutritionInfoInterface
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NutritionInfoController : NutritionInfoInterface {
    private val db = Firebase.firestore

    override suspend fun getAllNutritionInfo(uidEmail: String): List<NutritionInfo> {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val mealsCollectionRef = db.collection(uidEmail)
            .document("foods")
            .collection(currentDate)

        val snapshot = mealsCollectionRef.get().await()

        val meals = mutableListOf<NutritionInfo>()

        for (document in snapshot.documents) {
            val meal = document.toObject(NutritionInfo::class.java)
            if (meal != null) {
                meals.add(meal)
            }
        }
        Log.d("NutritionInfoController", "Meals $meals")

        return meals
    }


    override suspend fun postNutritionInfo(uidEmail: String, nutritionInfo: NutritionInfo) {
        try {
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val foodsCollectionRef = db.collection(uidEmail)
                .document("foods")
                .collection(currentDate)

            val mealDocRef = foodsCollectionRef.document(nutritionInfo.name)
            mealDocRef.set(nutritionInfo).await()

            Log.d("NutritionInfoController", "Nutrition info added successfully")
        } catch (e: Exception) {
            Log.e("NutritionInfoController", "Exception in postNutritionInfo: ${e.message}")
        }
    }
}
