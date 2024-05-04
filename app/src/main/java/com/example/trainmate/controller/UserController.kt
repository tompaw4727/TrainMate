package com.example.trainmate.controller

import com.example.trainmate.entity.UserData
import com.example.trainmate.repository.UserInterface
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserController : UserInterface {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun getData(documentId: String, callback: (UserData?) -> Unit) {
        firestore.document(documentId).get().await()

    }

    override suspend fun postData(data: UserData, collectionID: String, documentId: String) {
        firestore.collection(collectionID)
            .document(documentId)
            .set(data)
            .addOnSuccessListener {
                println("DocumentSnapshot added")
            }
            .addOnFailureListener { e ->
                println("Error adding document $e")
            }.await()
    }

    override suspend fun updateData(collectionID: String, documentId: String, newData: UserData) {
        firestore.collection(collectionID)
            .document(documentId).update().await()
    }

    override suspend fun deleteData(documentId: String) {
        firestore.document(documentId).delete().await()
    }

}