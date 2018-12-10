package com.example.dgenkov.findrepo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        val searchButton = findViewById<Button>(R.id.searchButton)


        searchButton.setOnClickListener {
            if(searchEditText.text.toString() != "") {
                val searchIntent = Intent(this,SearchResultActivity::class.java)
                searchIntent.putExtra("searchTerm", searchEditText.text.toString())

                startActivity(searchIntent)
            }

        }
    }
}
