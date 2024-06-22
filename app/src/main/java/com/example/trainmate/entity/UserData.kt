package com.example.trainmate.entity

data class UserData(
    var age: Int = 0,
    var sex: String = "",
    var weight: Double = 0.0,
    var height: Int = 0,
    var calories: Int = 0,
    var username: String = "",
)

fun UserData.toMap(): Map<String, Any> {
    val map = mutableMapOf<String, Any>()

    map["age"] = age
    map["sex"] = sex
    map["weight"] = weight
    map["height"] = height
    map["calories"] = calories
    map["username"] = username

    return map
}