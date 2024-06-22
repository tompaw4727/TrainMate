package com.example.trainmate.repository

import com.example.trainmate.entity.NutritionInfo
import com.example.trainmate.entity.UserData

interface NutritionInfoInterface {
    suspend fun getAllNutritionInfo(uidEmail: String): List<NutritionInfo>?

    suspend fun postNutritionInfo(uidEmail:String, nutritionInfo: NutritionInfo)

//    suspend fun updateUserData(userId: String, updatedUser: UserData)
//
//    suspend fun deleteUserData(userId: String)
}