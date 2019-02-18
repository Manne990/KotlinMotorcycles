package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import javax.inject.Inject

class MotorcyclesViewModel @Inject constructor(private val webservice: WebServiceInterface) : ViewModel(), MotorcyclesViewModelInterface {
    // Private Members
    private val _motorcycles: MutableLiveData<List<Motorcycle>> by lazy {
        MutableLiveData<List<Motorcycle>>().also {
            loadMotorcycles()
        }
    }

    // Public Functions
    override fun getMotorcycles(): LiveData<List<Motorcycle>> {
        return _motorcycles
    }

    // Private Functions
    private fun loadMotorcycles() {
        GlobalScope.launch {
            try {
                val response =  webservice.getMotorcycles()

                _motorcycles.postValue(response.data)

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