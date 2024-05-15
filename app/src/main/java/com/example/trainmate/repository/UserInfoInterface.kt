package com.example.trainmate.repository

import com.example.trainmate.entity.UserData

interface UserInfoInterface {
    suspend fun getUserData(userId: String): UserData?
    suspend fun postUserData(userId: String, userData: UserData)
    suspend fun updateUserData(userId: String, updatedUser: UserData)
    suspend fun deleteUserData(userId: String)
}