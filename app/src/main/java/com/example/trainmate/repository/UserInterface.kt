package com.example.trainmate.repository

import com.example.trainmate.entity.UserData

interface UserInterface {
    suspend fun getData(documentId: String, callback: (UserData?) -> Unit)
    suspend fun postData(data: UserData, collectionID: String, documentId: String)
    suspend fun updateData(collectionID: String, documentId: String, newData: UserData)
    suspend fun deleteData(documentId: String)
}