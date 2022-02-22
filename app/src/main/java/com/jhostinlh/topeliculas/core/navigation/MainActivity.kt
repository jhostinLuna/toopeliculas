package com.jhostinlh.topeliculas.core.navigation

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
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
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.functional.DialogCallback
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.features.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.features.vistaFragments.DialogShowError
import com.jhostinlh.topeliculas.features.vistaFragments.ListMoviesFragment

class MainActivity : AppCompatActivity(),PopUpDelegator {

    private lateinit var mAdView:AdView
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var toolbar: Toolbar
    private lateinit var navController:NavController
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout:DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleIntent(intent)
        //Para detectar click en menu de DrawerLayout
        navigationView = findViewById<NavigationView>(R.id.nav_view)
        toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
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
        initListeners()

        navigationView.menu.findItem(R.id.listMoviesDrawerLayout).setOnMenuItemClickListener {
            val bundle = Bundle()
            bundle.putString("nameList", Data.SERVICE_LATEST)
            navController.navigate(R.id.listMovies,bundle)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            true
        }
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
                R.id.listaFavoritos -> "Aquí tus favoritos"
                R.id.listMovies -> {
                    if (arguments?.getString("nameList").equals(Data.SERVICE_POPULATE)){
                        this.getString(R.string.label_populated)
                    }else if(arguments?.getString("nameList").equals(Data.SERVICE_TOP_RATED)){
                        this.getString(R.string.label_topRater)
                    }else if(arguments?.getString("nameList").equals(Data.SERVICE_LATEST)){
                        this.getString(R.string.label_latest)
                    }else{
                        this.getString(R.string.not_list_movies)
                    }
                }
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

        val miFrag = ListMoviesFragment()
        val args  = Bundle()
        args.putString("email", "jhostinlunadev@gmail.com")
        miFrag.arguments = args
    }

    private fun initListeners() {
        //Listeners de DrawerLayout o DrawerToogle
        navigationView.menu.findItem(R.id.listPopulateDrawerLayout).setOnMenuItemClickListener {
            val bundle = Bundle()
            bundle.putString("nameList", Data.SERVICE_POPULATE)
            navController.navigate(R.id.listMovies,bundle)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            true
        }
        navigationView.menu.findItem(R.id.listTopRatedDrawerLayout).setOnMenuItemClickListener {
            val bundle = Bundle()
            bundle.putString("nameList", Data.SERVICE_TOP_RATED)
            navController.navigate(R.id.listMovies,bundle)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            true
        }
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
        /*
        return item.onNavDestinationSelected(findNavController(R.id.fragmentContainerView4))
                || super.onOptionsItemSelected(item)

         */
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
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

    override fun showErrorWithRetry(
        title: String,
        message: String,
        positiveText: String,
        negativeText: String,
        callback: DialogCallback
    ) {
        // Implementar aqui el dialog con el que quereis mostrar los errores y en función
        // del boton pulsado llamar a callback.onAccept() o callback.onDecline() que lo que hace es
        // delegar al fragment
        val fragmentManager =supportFragmentManager
        val dialogShowError = DialogShowError()
        dialogShowError.dialogCallback = callback
        dialogShowError.title = title
        dialogShowError.message = message
        dialogShowError.positiveText = positiveText
        dialogShowError.negativeText = negativeText
        dialogShowError.show(supportFragmentManager,"DialogShowError")


    }
}