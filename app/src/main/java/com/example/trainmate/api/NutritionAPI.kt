package com.example.trainmate.api

import NutritionInfo
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.Properties

class NutritionAPI {
    companion object {
        private const val API_ENDPOINT = "https://api.api-ninjas.com/v1/nutrition"
    }

    fun getNutritionInfo(query: String): NutritionInfo? {
        val url = URL("$API_ENDPOINT?query=$query")
        val connection = url.openConnection() as HttpURLConnection

        val properties = Properties()
        properties.load(FileInputStream("local.properties"))
        val apiKey = properties.getProperty("API_KEY")

        connection.requestMethod = "GET"
        connection.setRequestProperty("X-Api-Key", apiKey)

        val responseCode = connection.responseCode
        println("Response Code: $responseCode")

        return if (responseCode == HttpURLConnection.HTTP_OK) {
            val objectMapper = ObjectMapper()
            val jsonResponse = connection.inputStream.bufferedReader().use { it.readText() }
            val nutritionInfoList = objectMapper.readValue(jsonResponse, Array<NutritionInfo>::class.java).toList()
            connection.disconnect()
            if (nutritionInfoList.isNotEmpty()) {
                nutritionInfoList[0]
            } else {
                null
            }
        } else {
            connection.disconnect()
            null
        }
    }
}



fun main() {
    val nutritionAPI = NutritionAPI()


    val query = "apple"
    val nutritionInfo = nutritionAPI.getNutritionInfo(query)


    if (nutritionInfo != null) {
        println("Nutrition Info for $query:")
        println("Calories: ${nutritionInfo.calories}")
        println("Protein: ${nutritionInfo.protein}")
        println("Carbohydrates: ${nutritionInfo.carbohydratesTotal}")
        println("Fat: ${nutritionInfo.fatTotal}")

    } else {
        println("Failed to retrieve nutrition info for $query.")
    }
}