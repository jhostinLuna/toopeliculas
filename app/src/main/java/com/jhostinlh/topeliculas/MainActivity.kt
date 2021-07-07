package com.jhostinlh.topeliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.jhostinlh.topeliculas.VistaFragments.ListTopRated.ListTopRatedViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        // Muestra el nombre de la app en toolbar si es true
        supportActionBar?.setDisplayShowTitleEnabled(true)


    }







}