package com.example.trainmate.repository

import NutritionInfo
import com.example.trainmate.entity.UserData

interface NutritionInfoInterface {
    suspend fun getAllNutritionInfo(userId: String): List<NutritionInfo>?

    suspend fun postNutritionInfo(userId: String, mealName: String)

//    suspend fun updateUserData(userId: String, updatedUser: UserData)
//
//    suspend fun deleteUserData(userId: String)
}