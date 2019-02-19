package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import javax.inject.Inject

class EditMotorcycleViewModel @Inject constructor(private val webservice: WebServiceInterface) : ViewModel(), EditMotorcycleViewModelInterface {
    // Public Properties
    override val motorcycle: MutableLiveData<Motorcycle> = MutableLiveData()

    // Public Functions
    override fun initWithPayload(payload: Motorcycle) {
        println("--- EditMotorcycleViewModel.initWithPayload: $payload")

        motorcycle.postValue(payload)
    }

    override fun saveMotorcycle() {
        println("--- EditMotorcycleViewModel.saveMotorcycle: ${motorcycle.value}")
    }
}