package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.service.AnalyticsService
import se.idoapps.kotlinmotorcycles.service.AnalyticsServiceInterface
import javax.inject.Inject

class MotorcyclesViewModel @Inject constructor(private val webservice: WebServiceInterface, private val analytics: AnalyticsServiceInterface) : ViewModel(), MotorcyclesViewModelInterface {
    // Public Properties
    override val motorcycles: MutableLiveData<List<Motorcycle>> = MutableLiveData()

    // Public Functions
    override suspend fun loadMotorcycles() {
        val response =  webservice.getMotorcycles()
        if (response.success) {
            motorcycles.postValue(response.data.sortedWith(compareBy({it.brand}, {it.model}, {it.year})))
        }
    }

    override suspend fun deleteMotorcycle(motorcycle: Motorcycle) {
        val response = webservice.deleteMotorcycle(motorcycle.objectId)
        if (response.success) {
            analytics.trackEvent(AnalyticsService.Events.DELETE_MOTORCYCLE, mapOf("Brand" to motorcycle.brand, "Model" to motorcycle.model, "Year" to motorcycle.year.toString()))
            loadMotorcycles()
        }
    }
}