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
            motorcycles.postValue(response.data)
        }
    }

    override fun addMotorcycle() {
        //TODO: Start the add motorcycle process and update the collection after the process is done
    }

    override fun editMotorcycle(mc: Motorcycle) {
        //TODO: Start the edit motorcycle process and update the collection after the process is done
    }

    override fun deleteMotorcycle(mc: Motorcycle) {
        //TODO: Start the delete motorcycle process and update the collection after the process is done
    }
}