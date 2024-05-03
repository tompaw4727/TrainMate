package com.example.trainmate

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class NutritionActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)

        val pieChart: PieChart = findViewById(R.id.chart)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(50f, "dupa"))
        entries.add(PieEntry(100f, "kupa"))

        val pieDataSet = PieDataSet(entries, "zbiur")
        pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val pieData = PieData(pieDataSet)
        pieChart.data = pieData

        pieChart.description.isEnabled = false
        pieChart.animateY(1000)
        pieChart.invalidate()
    }


}