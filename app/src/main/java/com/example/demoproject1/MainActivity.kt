package com.example.demoproject1

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPref1: SharedPreferences
    private lateinit var editor1 : SharedPreferences.Editor
    private val personList: MutableList<person> = mutableListOf()
    var personData1 : MutableList<person> = mutableListOf()

   lateinit var textResult : TextView
   lateinit var textInfo : TextView
    private var bmi2Digit : Float = 0.0f

    lateinit var height : String
    lateinit var name : String
    lateinit var weight : String

    var cnt =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val edHeight = findViewById<EditText>(R.id.edHeight)
        val edWeight = findViewById<EditText>(R.id.edWeight)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
            textResult = findViewById(R.id.txtResult)
            textInfo = findViewById(R.id.txtInfo)
        val edName = findViewById<EditText>(R.id.edName)
         val btnHistory= findViewById<Button>(R.id.btnHistory)


            //start the second activity
            btnHistory.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

            sharedPref1 = getSharedPreferences("sf", MODE_PRIVATE)
            btnSubmit.setOnClickListener {
                height = edHeight.text.toString()
             weight = edWeight.text.toString()
             name = edName.text.toString()

            if(validateInput(name, height, weight)) {

                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                bmi2Digit = String.format("%.2f", bmi).toFloat()
                when{
                    bmi2Digit < 18.5->{
                        textResult.text = "$bmi2Digit UnderWeight"
                    }
                    bmi2Digit in 18.5.. 24.99 ->{
                        textResult.text = "$bmi2Digit Healthy"
                    }
                    bmi2Digit in 25.00 .. 29.99->{
                        textResult.text = "$bmi2Digit Overweight"
                    }
                    bmi2Digit > 29.99->{
                        textResult.text = "$bmi2Digit Obese"
                    }
                }
                textInfo.text = "(Normal Range is 18.5 - 24.9)"
            }
                else{
                    textResult.text = ""
                     textInfo.text = ""
                }

               if (validateInput(name, height, weight)) {
                    addItemToList(name, height.toInt(), weight.toInt(), bmi2Digit)
                   saveData()
                }
            }

    }

    //shared Preference
    private fun saveData() {
        editor1 = sharedPref1.edit()
        val gson = Gson()
        val json: String = gson.toJson(personList)

        val personData = sharedPref1.getString("person", json)
        val type: Type = object : TypeToken<ArrayList<person?>?>() {}.type
        personData1 = gson.fromJson(personData, type) as ArrayList<person>
        personData1.addAll(listOf(personList.get(cnt)))

        val json2 : String = gson.toJson(personData1)
        editor1.putString("person", json2)
        editor1.apply()
        cnt++;

      //  Toast.makeText(this, "Saved Person List to Shared preferences. ", Toast.LENGTH_SHORT)
            //.show()
    }

    private fun validateInput(name: String?, height: String?, weight: String?) : Boolean{
        return when{
            name.isNullOrEmpty() && height.isNullOrEmpty() && weight.isNullOrEmpty() ->{
                Toast.makeText(this, "Please Enter Name, Height & Weight", Toast.LENGTH_SHORT).show()
                return false
            }
            name.isNullOrEmpty() && height.isNullOrEmpty()->{
                Toast.makeText(this, "Please Enter Name & Height", Toast.LENGTH_SHORT).show()
                return false
            }
            height.isNullOrEmpty() && weight.isNullOrEmpty() ->{
                Toast.makeText(this, "Please Enter Height & Weight", Toast.LENGTH_SHORT).show()
                return false
            }
            name.isNullOrEmpty() && weight.isNullOrEmpty() ->{
                Toast.makeText(this, "Please Enter Name & Weight", Toast.LENGTH_SHORT).show()
                return false
            }
            name.isNullOrEmpty() ->{
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show()
                return false
            }
            height.isNullOrEmpty()->{
                Toast.makeText(this, "Please Enter Height", Toast.LENGTH_SHORT).show()
                return false
            }
            weight.isNullOrEmpty()->{
                Toast.makeText(this, "Please Enter Weight", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun addItemToList(name: String, height: Int, weight: Int, bmi: Float) {
        personList.add(person(name, weight, height, bmi))
    }

  // handling the rotation

   /* override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bmiVal = bmi2Digit
        outState.putFloat("bmi1", bmiVal)
    }
  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null) {
            val bmiVal =  savedInstanceState.getFloat("bmi1", 0.0f)
            bmi2Digit = bmiVal
            when{
                bmi2Digit < 18.5->{ textResult.text = "$bmiVal UnderWeight"
                }
                bmi2Digit in 18.5.. 24.99 ->{
                    textResult.text = "$bmiVal Healthy"
                }
                bmi2Digit in 25.00 .. 29.99->{
                    textResult.text = "$bmiVal Overweight"
                }
                bmi2Digit > 29.99->{
                    textResult.text = "$bmiVal Obese"
                }
            }
            textInfo.text = "(Normal Range is 18.5 - 24.9)"
        }

    }*/


}
