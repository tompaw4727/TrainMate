package com.example.trainmate.homepage.calorie.track

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainmate.BaseActivity
import com.example.trainmate.R
import com.example.trainmate.controller.NutritionInfoController
import com.example.trainmate.controller.UserProfileController
import com.example.trainmate.entity.NutritionInfo
import com.example.trainmate.homepage.HomePageActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch

class CalorieTrackActivity : BaseActivity(), ButtonClickListener, View.OnClickListener {

    private lateinit var pieChart: PieChart
    private lateinit var recyclerViewAddItem: RecyclerView
    private lateinit var addNutritionInfoAdapter: AddNutritionInfoAdapter
    private lateinit var recyclerViewShowItems: RecyclerView
    private lateinit var showNutritionInfoAdapter: ShowNutritionInfoAdapter
    private val controller = NutritionInfoController()
    private val userProfileController = UserProfileController()

    private var uidEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calorie_track_activity)

        uidEmail = intent.getStringExtra("uID_email")

        initRecyclerViews()
        pieChart = findViewById(R.id.chart)

        uidEmail?.let { loadNutritionInfo(it) } ?: showErrorSnackBar("Error: uid_email is null", true)
    }

    override fun onButtonClick(nutritionInfo: NutritionInfo) {
        uidEmail?.let {
            lifecycleScope.launch {
                handlePostNutritionInfo(it, nutritionInfo)
            }
        } ?: showErrorSnackBar("Error: uid_email is null", true)
    }

    private fun initRecyclerViews() {
        recyclerViewAddItem = findViewById(R.id.recyclerView_add_item)
        recyclerViewAddItem.layoutManager = LinearLayoutManager(this)
        addNutritionInfoAdapter = AddNutritionInfoAdapter(this)
        recyclerViewAddItem.adapter = addNutritionInfoAdapter

        recyclerViewShowItems = findViewById(R.id.recyclerView_show_items)
        recyclerViewShowItems.layoutManager = GridLayoutManager(this, 2)
        showNutritionInfoAdapter = ShowNutritionInfoAdapter()
        recyclerViewShowItems.adapter = showNutritionInfoAdapter
    }

    private fun handlePostNutritionInfo(uidEmail: String, nutritionInfo: NutritionInfo) {
        lifecycleScope.launch {
            try {
                controller.postNutritionInfo(uidEmail, nutritionInfo)
                loadNutritionInfo(uidEmail)
                showErrorSnackBar("Nutrition info added successfully", false)
            } catch (e: Exception) {
                showErrorSnackBar("Failed to add nutrition info", true)
            }
        }
    }

    private fun loadNutritionInfo(uidEmail: String) {
        lifecycleScope.launch {
            try {
                val nutritionInfoList = controller.getAllNutritionInfo(uidEmail)
                showNutritionInfoAdapter.setNutritionInfoList(nutritionInfoList)

                val userData = userProfileController.getUserData(uidEmail)
                userData?.let { updateUserNutritionalInfo(it.calories.toDouble(), nutritionInfoList) }
            } catch (e: Exception) {
                showErrorSnackBar("Failed to load nutrition info: $e", true)
            }
        }
    }

    private fun updateUserNutritionalInfo(dailyCalorieRequirement: Double, nutritionInfoList: List<NutritionInfo>) {
        val (totalProtein, totalFat, totalCarbs, totalCalories) = calculateNutritionTotals(nutritionInfoList)

        val (dailyProteinRequirement, dailyFatRequirement, dailyCarbsRequirement) = calculateDailyRequirements(dailyCalorieRequirement)

        val (proteinPercentage, fatPercentage, carbsPercentage, caloriesPercentage) = calculateNutrientPercentages(
            totalProtein, dailyProteinRequirement,
            totalFat, dailyFatRequirement,
            totalCarbs, dailyCarbsRequirement,
            totalCalories, dailyCalorieRequirement
        )

        setupPieChart(proteinPercentage, fatPercentage, carbsPercentage, caloriesPercentage)
    }

    private fun calculateNutritionTotals(nutritionInfoList: List<NutritionInfo>): NutritionTotals {
        var totalProtein = 0.0
        var totalFat = 0.0
        var totalCarbs = 0.0
        var totalCalories = 0.0

        for (nutritionInfo in nutritionInfoList) {
            val weight = nutritionInfo.weight
            totalProtein += (nutritionInfo.proteinPer100g * weight / 100)
            totalFat += (nutritionInfo.fatPer100g * weight / 100)
            totalCarbs += (nutritionInfo.carbohydratesPer100g * weight / 100)
            totalCalories += (nutritionInfo.caloriesPer100g * weight / 100)
        }

        return NutritionTotals(totalProtein, totalFat, totalCarbs, totalCalories)
    }

    private fun calculateDailyRequirements(dailyCalorieRequirement: Double): Triple<Double, Double, Double> {
        val dailyProteinRequirement = 0.2 * dailyCalorieRequirement
        val dailyFatRequirement = 0.3 * dailyCalorieRequirement
        val dailyCarbsRequirement = 0.5 * dailyCalorieRequirement
        return Triple(dailyProteinRequirement, dailyFatRequirement, dailyCarbsRequirement)
    }

    private fun calculateNutrientPercentages(
        totalProtein: Double, dailyProteinRequirement: Double,
        totalFat: Double, dailyFatRequirement: Double,
        totalCarbs: Double, dailyCarbsRequirement: Double,
        totalCalories: Double, dailyCalorieRequirement: Double
    ): NutrientPercentages {
        val proteinPercentage = (totalProtein / dailyProteinRequirement) * 100
        val fatPercentage = (totalFat / dailyFatRequirement) * 100
        val carbsPercentage = (totalCarbs / dailyCarbsRequirement) * 100
        val caloriesPercentage = (totalCalories / dailyCalorieRequirement) * 100

        return NutrientPercentages(proteinPercentage.toFloat(), fatPercentage.toFloat(), carbsPercentage.toFloat(), caloriesPercentage.toFloat())
    }

    private fun setupPieChart(proteinPercentage: Float, fatPercentage: Float, carbsPercentage: Float, caloriesPercentage: Float) {
        val entries = listOf(
            PieEntry(proteinPercentage, "Protein [%]"),
            PieEntry(fatPercentage, "Fat [%]"),
            PieEntry(carbsPercentage, "Carbs [%]"),
            PieEntry(caloriesPercentage, "Calories [%]")
        )

        val dataSet = PieDataSet(entries, "Nutrient Consumption")

        val colors = listOf("#9b69f1", "#5f14e0", "#e0142f", "#f1697a")
        val colorInts = colors.map { Color.parseColor(it) }
        dataSet.colors = colorInts
        dataSet.valueTextSize = 12f

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.holeRadius = 0f

        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setHoleColor(171221)
        pieChart.animateY(1000)
        pieChart.invalidate()
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.button_back -> {
                    val intent = Intent(this, HomePageActivity::class.java)
                    intent.putExtra("uID_email", uidEmail)
                    startActivity(intent)
                }
            }
        }
    }

    data class NutritionTotals(val totalProtein: Double, val totalFat: Double, val totalCarbs: Double, val totalCalories: Double)
    data class NutrientPercentages(val proteinPercentage: Float, val fatPercentage: Float, val carbsPercentage: Float, val caloriesPercentage: Float)
}
