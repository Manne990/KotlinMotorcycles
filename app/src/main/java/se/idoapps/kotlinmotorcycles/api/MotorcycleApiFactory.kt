package se.idoapps.kotlinmotorcycles.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MotorcycleApiFactory {
    // Private Members
    private const val BASE_URL = "https://api.backendless.com/8497684E-2B7B-1E53-FF7C-3C2B0CE99700/282CDAE9-FE22-748C-FF61-DC3168DB0D00/"

    // Public Functions
    fun makeMotorcycleService(): MotorcycleApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(MotorcycleApi::class.java)
    }
}
