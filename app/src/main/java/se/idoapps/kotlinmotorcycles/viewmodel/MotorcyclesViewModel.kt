package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import se.idoapps.kotlinmotorcycles.api.MotorcycleApi
import se.idoapps.kotlinmotorcycles.api.MotorcycleApiFactory
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import javax.inject.Inject

class MotorcyclesViewModel @Inject constructor() : ViewModel() {
    // Private Members
    private val _motorcycles: MutableLiveData<List<Motorcycle>> by lazy {
        MutableLiveData<List<Motorcycle>>().also {
            loadMotorcycles()
        }
    }

    // Public Functions
    fun getMotorcycles(): LiveData<List<Motorcycle>> {
        return _motorcycles
    }

    // Private Functions
    private fun loadMotorcycles() {
        val service = MotorcycleApiFactory.makeMotorcycleService()

        GlobalScope.launch {
            val request = service.getMotorcyclesAsync()
            try {
                val response = request.await()

                response?.isSuccessful.let {
                    println(response?.body())
                    _motorcycles.postValue(response?.body())
                }

            } catch (e: HttpException) {
                println(e.code())
                println(e.message)
            } catch (e: Throwable) {
                println("Ooops: Something else went wrong")
                println(e.message)
            }
        }
    }
}