import com.fasterxml.jackson.annotation.JsonProperty

data class NutritionInfo(
    @JsonProperty("name") val name: String,
    @JsonProperty("calories") val calories: Double,
    @JsonProperty("serving_size_g") val servingSize: Double,
    @JsonProperty("fat_total_g") val fatTotal: Double,
    @JsonProperty("fat_saturated_g") val fatSaturated: Double,
    @JsonProperty("protein_g") val protein: Double,
    @JsonProperty("sodium_mg") val sodium: Int,
    @JsonProperty("potassium_mg") val potassium: Int,
    @JsonProperty("cholesterol_mg") val cholesterol: Int,
    @JsonProperty("carbohydrates_total_g") val carbohydratesTotal: Double,
    @JsonProperty("fiber_g") val fiber: Double,
    @JsonProperty("sugar_g") val sugar: Double
) {
    constructor() : this("", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0.0, 0.0, 0.0)

    override fun toString(): String {
        return "NutritionInfo(name='$name', calories=$calories, servingSize=$servingSize, fatTotal=$fatTotal, fatSaturated=$fatSaturated, protein=$protein, sodium=$sodium, potassium=$potassium, cholesterol=$cholesterol, carbohydratesTotal=$carbohydratesTotal, fiber=$fiber, sugar=$sugar)"
    }
}
