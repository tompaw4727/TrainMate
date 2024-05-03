package com.example.trainmate.api

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.util.Properties

data class NutritionInfo(
    val name: String,
    val calories: Double,
    val servingSize: Double,
    val fatTotal: Double,
    val fatSaturated: Double,
    val protein: Double,
    val sodium: Int,
    val potassium: Int,
    val cholesterol: Int,
    val carbohydratesTotal: Double,
    val fiber: Double,
    val sugar: Double
)

class NutritionAPI {
    companion object {
        private const val API_ENDPOINT = "https://api.api-ninjas.com/v1/nutrition"
    }

    fun getNutritionInfo(query: String): List<NutritionInfo>? {
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
            nutritionInfoList
        } else {
            connection.disconnect()
            null
        }
    }
}