package com.example.demoproject1

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SecondActivity : AppCompatActivity() {

    var personList: MutableList<person> = mutableListOf()

    private lateinit var secondActivityAdapter: SecondActivityRecycler
    private lateinit var recyclerView : RecyclerView

    private lateinit var sharedPref1: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        sharedPref1 = getSharedPreferences("sf", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref1.getString("person", null)
        val type: Type = object : TypeToken<ArrayList<person?>?>() {}.type
        personList = gson.fromJson(json, type) as ArrayList<person>

        recyclerView = findViewById(R.id.myRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        secondActivityAdapter = SecondActivityRecycler(personList)
        recyclerView.adapter = secondActivityAdapter

    }
}