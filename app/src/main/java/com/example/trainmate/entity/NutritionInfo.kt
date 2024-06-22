package com.example.trainmate.entity

data class NutritionInfo(
    val name: String = "",
    val caloriesPer100g: Double = 0.0,
    val carbohydratesPer100g: Double = 0.0,
    val fatPer100g: Double = 0.0,
    val proteinPer100g: Double = 0.0,
    val weight: Double = 0.0
)