package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.service.AnalyticsServiceAbstractions
import se.idoapps.kotlinmotorcycles.service.AnalyticsServiceInterface
import javax.inject.Inject

class MotorcyclesViewModel @Inject constructor(private val webservice: WebServiceInterface, private val analytics: AnalyticsServiceInterface) : ViewModel(), MotorcyclesViewModelInterface {
    // Private Members
    private val _motorcycles: MutableLiveData<List<Motorcycle>> = MutableLiveData()

    // Public Properties
    override val motorcycles: LiveData<List<Motorcycle>> = _motorcycles

    // Public Functions
    override suspend fun loadMotorcyclesAsync() {
        val response =  webservice.getMotorcycles()
        if (response.success) {
            _motorcycles.postValue(response.data.sortedWith(compareBy({it.brand}, {it.model}, {it.year})))
        }
    }

    override suspend fun deleteMotorcycleAsync(motorcycle: Motorcycle) {
        val response = webservice.deleteMotorcycle(motorcycle.objectId)
        if (response.success) {
            analytics.trackEvent(AnalyticsServiceAbstractions.Events.DELETE_MOTORCYCLE, mapOf("Brand" to motorcycle.brand, "Model" to motorcycle.model, "Year" to motorcycle.year.toString()))
            loadMotorcyclesAsync()
        }
    }
}