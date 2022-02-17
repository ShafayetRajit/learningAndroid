package com.example.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_datepicker.setOnClickListener { view ->
            clickDatePicker(view)
            Toast.makeText(this, "Choose Date of Birth.", Toast.LENGTH_LONG).show()
        }

    }

    fun clickDatePicker(view: View) {

        val myCalendar = Calendar.getInstance()

        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val date = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dateInMins = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, date ->
            val selectedDate = "$date/${month+1}/$year"
            tv_date.setText(selectedDate)

            val sdf = SimpleDateFormat("dd/MM/yyyy") //d = day in month, D = day in year
            val parsedDate = sdf.parse(selectedDate)
            val selectedDateInMins = parsedDate.time / 60000

            val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
            val currentDateInMins = currentDate.time / 60000

            val ageInMins = currentDateInMins - selectedDateInMins
            tv_minutes.setText(ageInMins.toString())

            val name = et_name.text
            tv_descMinutes.setText("$name's age is minutes!")

        }, year, month, date)

        dateInMins.datePicker.setMaxDate(Date().time - 86400000)
        dateInMins.show()

    }
}