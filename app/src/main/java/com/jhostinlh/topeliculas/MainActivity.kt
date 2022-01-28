package com.jhostinlh.topeliculas

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.jhostinlh.topeliculas.vistaFragments.ListTopRated
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie

class MainActivity : AppCompatActivity() {

    private lateinit var mAdView:AdView
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var toolbar: Toolbar
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleIntent(intent)

        toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        // Muestra el nombre de la app en toolbar si es true
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //Obtengo el navhostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView4) as NavHostFragment
        //Apartir de navhostFragment obtengo el navcontroller
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        // Para controlar NavigationView

        setupActionBarWithNavController(navController, appBarConfiguration)

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)
        setupActionBarWithNavController(navController,drawerLayout)
        /* Sin NavigationUI
        val toogle = ActionBarDrawerToggle(
            this,drawerLayout,toolbar,R.string.openDrawerContentDescRes,R.string.closeDrawerContentDescRes)
        drawerLayout.addDrawerListener(toogle)

         */
        /*para Poner Screen a FUll
        navController.addOnDestinationChangedListener { _, destination, _ ->
           if(destination.id == R.id.full_screen_destination) {
               toolbar.visibility = View.GONE
               bottomNavigationView.visibility = View.GONE
           } else {
               toolbar.visibility = View.VISIBLE
               bottomNavigationView.visibility = View.VISIBLE
           }
        }
         */
        navController.addOnDestinationChangedListener { navController, navDestination, arguments ->
            /*
            when (navDestination.id){
                R.id.detallePelicula -> toolbar.isVisible =false
                else -> toolbar.isVisible = true
            }
            val movie = arguments?.getParcelable<Movie>("peli")
            movie?.let {  }
            */
             //= arguments?.getBoolean("ShowAppBar", false) == true
            toolbar.title =  when(navDestination.id){
                R.id.buscarPeli -> "Busca por título"
                R.id.groupList -> "Bienvenido!!"
                R.id.listTopRated -> "Lista con mejor puntuación"
                R.id.listaFavoritos -> "Aquí tus favoritos"
                R.id.listPopulate -> "Lista de Populares"
                R.id.listLatest -> "Ultimas de Cine"
                R.id.detallePelicula -> arguments?.getParcelable<Movie>("peli")?.title
                else -> {"webyapps.com"}
            }
        }
        //ADMOB
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

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

        //Barra de Navegación Inferior

        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

        val miFrag = ListTopRated()
        val args  = Bundle()
        args.putString("email", "jhostinlunadev@gmail.com")
        miFrag.arguments = args
    }
    //Para controlar la navegacion hacia atras
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView4)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu):Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.buscarPelicula).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        /*
        val searchItem = menu.findItem(R.id.buscarPelicula)
        val searchView = searchItem.actionView as SearchView
        searchView.apply{
            queryHint = "Search"
            isIconified = false
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {

                    // start the search
                    if (query != null) {
                        Log.d("mensaje",query)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // nothing to do
                    if (newText != null) {
                        Log.d("mensaje", newText)
                    }
                    return false
                }
            })
        }
         */
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
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            val queryBundle = Bundle()
            queryBundle.putString("query",query)
            navController.navigate(R.id.buscarPeli,queryBundle)
        }
    }


}