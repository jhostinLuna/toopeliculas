package com.jhostinlh.topeliculas.core.di

import com.jhostinlh.topeliculas.BuildConfig
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.core.platform.ContextHandler
import com.jhostinlh.topeliculas.core.platform.NetworkHandler
import com.jhostinlh.topeliculas.features.news.MoviesLocal
import com.jhostinlh.topeliculas.features.news.MoviesRepository
import com.jhostinlh.topeliculas.features.news.MoviesService
import com.jhostinlh.topeliculas.features.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.features.vistaFragments.ListFavoritesFragment
import com.jhostinlh.topeliculas.features.vistaFragments.adaptadores.FavoritosAdapterRecyclerView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { ContextHandler(get()) }
    factory { NetworkHandler(get()) }
    single {
        Retrofit.Builder()
            .baseUrl(Data.URL_BASE)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    //Si necesitamos el builder para proporcionarle otra urlbase
    single {
        Retrofit.Builder()
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
    }
}


val dataSourceModule = module {
    factory { MoviesService(get()) }
}

val databaseModule = module {
    factory { MoviesLocal(get()) }
}
val viewModelModule = module {
    viewModel { ShareRepoViewModel(get()) }
}
val repositoryModule = module {
    factory<MoviesRepository> { MoviesRepository.Network(get(), get(), get()) }
}
val favoritesAdapterModule = module {
    scope<ListFavoritesFragment> {
        scoped { FavoritosAdapterRecyclerView(get()) }
    }
}

private fun createClient(): OkHttpClient {
    val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
    }
    return okHttpClientBuilder.build()
}
