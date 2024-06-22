package com.example.trainmate.homepage.calorie.track


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.trainmate.R
import com.example.trainmate.entity.NutritionInfo

interface ButtonClickListener {
    fun onButtonClick(nutritionInfo: NutritionInfo)
}

class AddNutritionInfoAdapter(
    private val buttonClickListener: ButtonClickListener
) : RecyclerView.Adapter<AddNutritionInfoAdapter.ViewHolder>() {

    private var expandedPosition = RecyclerView.NO_POSITION

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val detailsLayout: LinearLayout = view.findViewById(R.id.detailsLayout)
        val editTextName: EditText = view.findViewById(R.id.editText_name)
        val editTextCalories: EditText = view.findViewById(R.id.editText_calories)
        val editTextFat: EditText = view.findViewById(R.id.editText_fat)
        val editTextProtein: EditText = view.findViewById(R.id.editText_protein)
        val editTextCarbohydrates: EditText = view.findViewById(R.id.editText_carbohydrates)
        val editTextWeight: EditText = view.findViewById(R.id.editText_weight)
        val addButton: Button = view.findViewById(R.id.button_add)

        init {
            view.setOnClickListener {
                if (expandedPosition == adapterPosition) {
                    expandedPosition = RecyclerView.NO_POSITION
                    notifyItemChanged(adapterPosition)
                } else {
                    val oldPosition = expandedPosition
                    expandedPosition = adapterPosition
                    notifyItemChanged(oldPosition)
                    notifyItemChanged(expandedPosition)
                }
            }

            addButton.setOnClickListener {
                val name = editTextName.text.toString()
                val calories = editTextCalories.text.toString().toDoubleOrNull() ?: 0.0
                val fat = editTextFat.text.toString().toDoubleOrNull() ?: 0.0
                val protein = editTextProtein.text.toString().toDoubleOrNull() ?: 0.0
                val carbs = editTextCarbohydrates.text.toString().toDoubleOrNull() ?: 0.0
                val weight = editTextWeight.text.toString().toDoubleOrNull() ?: 0.0
                val nutritionInfo = NutritionInfo(name, calories, fat, protein, carbs, weight)
                buttonClickListener.onButtonClick(nutritionInfo)
                expandedPosition = RecyclerView.NO_POSITION
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calorie_track_add_nutrition_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isExpanded = position == expandedPosition
        holder.detailsLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
    }

    override fun getItemCount() = 1
}
