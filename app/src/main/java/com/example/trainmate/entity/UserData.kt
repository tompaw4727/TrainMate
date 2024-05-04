package com.example.trainmate.entity

data class UserData(
    var name: String = "",
    var age: Int = 0,
    var sex: String = "",
    var weight: Double = 0.0,
    var height: Int = 0,
)
{
    override fun toString(): String {
        return "UserData(name='$name', age=$age, sex='$sex', weight=$weight, height=$height)"
    }
}
