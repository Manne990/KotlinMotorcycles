package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import javax.inject.Inject

class EditMotorcycleViewModel @Inject constructor(private val webservice: WebServiceInterface) : ViewModel(), EditMotorcycleViewModelInterface {
    // Public Properties
    override val data: MutableLiveData<Motorcycle> = MutableLiveData()
    override var motorcycle: Motorcycle = Motorcycle.empty()

    // Public Functions
    override fun initWithPayload(payload: Motorcycle?) {
        motorcycle = payload ?: Motorcycle.empty()

        data.postValue(motorcycle)
    }

    override fun saveMotorcycle() {
        val result = webservice.saveMotorcycle(motorcycle)
        if (!result.success) {
            println("EditMotorcycleViewModel.saveMotorcycle FAILED!")
        }
    }
}