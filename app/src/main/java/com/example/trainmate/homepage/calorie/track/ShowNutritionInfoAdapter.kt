package com.example.trainmate.homepage.calorie.track

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainmate.R
import com.example.trainmate.entity.NutritionInfo

class ShowNutritionInfoAdapter : RecyclerView.Adapter<ShowNutritionInfoAdapter.NutritionInfoViewHolder>() {

    private var nutritionInfoList: List<NutritionInfo> = emptyList()

    fun setNutritionInfoList(nutritionInfoList: List<NutritionInfo>) {
        this.nutritionInfoList = nutritionInfoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calorie_track_show_nutrition_items, parent, false)
        return NutritionInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: NutritionInfoViewHolder, position: Int) {
        val nutritionInfo = nutritionInfoList[position]
        holder.mealName.text = nutritionInfo.name
        holder.mealCalories.text = "Calories ${nutritionInfo.caloriesPer100g}"
        holder.mealFat.text = "Fat ${nutritionInfo.fatPer100g}"
        holder.mealProtein.text = "Protein ${nutritionInfo.proteinPer100g}"
        holder.mealCarbohydrates.text = "Carbohydrates ${nutritionInfo.carbohydratesPer100g}"
        holder.mealWeight.text = "Weight ${nutritionInfo.weight}"
    }

    override fun getItemCount(): Int = nutritionInfoList.size

    class NutritionInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealName: TextView = itemView.findViewById(R.id.meal_name)
        val mealCalories: TextView = itemView.findViewById(R.id.meal_calories)
        val mealFat: TextView = itemView.findViewById(R.id.meal_fat)
        val mealProtein: TextView = itemView.findViewById(R.id.meal_protein)
        val mealCarbohydrates: TextView = itemView.findViewById(R.id.meal_carbohydrates)
        val mealWeight: TextView = itemView.findViewById(R.id.meal_weight)
    }
}
