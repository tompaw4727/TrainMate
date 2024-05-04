package com.example.trainmate.homepage

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trainmate.R
import com.github.mikephil.charting.charts.PieChart

class CalorieTrackActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private val arrayList = arrayListOf("placeholder1", "placeholder2", "placeholder3")
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorie_track)

        val pieChart: PieChart = findViewById(R.id.chart)
        textView = findViewById(R.id.input_meal)

        textView.setOnClickListener {
            dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_searchable_spinner)
            dialog.window?.setLayout(650, 800)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val editText: EditText = dialog.findViewById(R.id.edit_meal)
            val listView: ListView = dialog.findViewById(R.id.list_view)

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
            listView.adapter = adapter

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            listView.setOnItemClickListener { _, _, position, _ ->
                textView.text = adapter.getItem(position)
                dialog.dismiss()
            }
        }
    }
}


//val entries = ArrayList<PieEntry>()
//entries.add(PieEntry(50f, "2"))
//entries.add(PieEntry(100f, "1"))
//
//val pieDataSet = PieDataSet(entries, "zbiur")
//pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
//
//val pieData = PieData(pieDataSet)
//pieChart.data = pieData
//
//pieChart.description.isEnabled = false
//pieChart.animateY(1000)
//pieChart.invalidate()