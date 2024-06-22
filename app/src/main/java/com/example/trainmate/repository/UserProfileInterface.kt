package com.example.trainmate.repository

import com.example.trainmate.entity.UserData

interface UserProfileInterface {
    suspend fun getUserData(uidEmail: String): UserData?
    suspend fun postUserData(uidEmail: String, userData: UserData)
    suspend fun updateUserData(uidEmail: String, updatedUser: UserData)
    suspend fun deleteUserData(userId: String)
}