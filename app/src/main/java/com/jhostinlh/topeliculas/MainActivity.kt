package com.jhostinlh.topeliculas

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.jhostinlh.topeliculas.vistaFragments.ListTopRated

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        // Muestra el nombre de la app en toolbar si es true
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //Obtengo el navhostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView4) as NavHostFragment
        //Apartir de navhostFragment obtengo el navcontroller
        val navController = navHostFragment.navController
        /*
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        val navController2 = findNavController(R.id.fragmentContainerView4)

         */
        val appBarConfiguration2 = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.my_toolbar)
            .setupWithNavController(navController, appBarConfiguration2)




        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, 12)
            param(FirebaseAnalytics.Param.ITEM_NAME, "jhostin")
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        }
        firebaseAnalytics.setUserProperty("favorite_food", "patatas")

        firebaseAnalytics.logEvent("share_image") {
            param("image_name", "nameImagen")
            param("full_text", "es una prueba")
        }
        val parameters = Bundle().apply {
            this.putString("level_name", "Caverns01")
            this.putInt("level_difficulty", 4)
        }

        firebaseAnalytics.setDefaultEventParameters(parameters)
        handleIntent(intent)

        //Barra de Navegación Inferior

        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

        val miFrag: ListTopRated = ListTopRated()
        val args:Bundle  = Bundle()
        args.putString("email", "jhostinlunadev@gmail.com");
        miFrag.arguments = args
    }

    override fun onCreateOptionsMenu(menu: Menu):Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.fragmentContainerView4))
                || super.onOptionsItemSelected(item)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }


    private fun handleIntent(intent: Intent) {
        Log.i("mensaje","entra en if")

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.i("mensaje","entra en if")
        }
    }


}