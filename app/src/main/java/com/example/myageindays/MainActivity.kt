package com.example.myageindays

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var buttonSelectDate: Button? = null
    private var linearResult: LinearLayout? = null
    private var textSelectedDate: TextView? = null
    private var textDays: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSelectDate = findViewById(R.id.buttonSelectDate)
        linearResult = findViewById(R.id.linearResult)
        textSelectedDate = findViewById(R.id.textSelectedDate)
        textDays = findViewById(R.id.textDays)

        buttonSelectDate?.setOnClickListener {
            clickButton()
        }
    }

    override fun onStart() {
        super.onStart()
        linearResult?.visibility = View.INVISIBLE
    }

    private fun clickButton() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener{ _, selectedYear, selectedMonth, selectedDay ->
                linearResult?.visibility = View.VISIBLE

                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"

                textSelectedDate?.text = selectedDate

                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val date = simpleDateFormat.parse(selectedDate)

                date?.let {
                    val selectedDateInDays = date.time / 86400000 // 1000 * 60 * 60 * 24 - millis to days

                    val currentDate = simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))

                    currentDate?.let {
                        val currentDateInDays = currentDate.time / 86400000 //86400000 is 1 day in milliseconds

                        val differenceInDays = currentDateInDays - selectedDateInDays

                        textDays?.text = "$differenceInDays"
                    }
                }

            }, year, month, day
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 86400000

        datePickerDialog.show()
    }
}