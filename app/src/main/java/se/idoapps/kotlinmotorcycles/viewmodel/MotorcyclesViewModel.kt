package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import javax.inject.Inject

class MotorcyclesViewModel @Inject constructor(private val webservice: WebServiceInterface) : ViewModel(), MotorcyclesViewModelInterface {
    // Public Properties
    override val motorcycles: MutableLiveData<List<Motorcycle>> = MutableLiveData()

    // Public Functions
    override fun loadMotorcycles() {
        val response =  webservice.getMotorcycles()
        if (response.success) {
            motorcycles.postValue(response.data.sortedWith(compareBy({it.brand}, {it.model}, {it.year})))
        }
    }
}