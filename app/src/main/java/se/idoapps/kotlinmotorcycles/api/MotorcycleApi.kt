package se.idoapps.kotlinmotorcycles.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import se.idoapps.kotlinmotorcycles.model.Motorcycle

interface MotorcycleApi {
    @GET("data/Motorcycles")
    fun getMotorcyclesAsync(): Deferred<Response<List<Motorcycle>>>
}