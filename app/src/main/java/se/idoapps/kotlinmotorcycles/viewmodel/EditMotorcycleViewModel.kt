package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.service.*
import javax.inject.Inject

class EditMotorcycleViewModel @Inject constructor(private val webservice: WebServiceInterface, private val analytics: AnalyticsServiceInterface) : ViewModel(), EditMotorcycleViewModelInterface {
    // Public Properties
    override val data: MutableLiveData<Motorcycle> = MutableLiveData()
    override var motorcycle: Motorcycle = Motorcycle.empty()

    // Public Functions
    override fun initWithPayload(payload: Motorcycle?) {
        // Init
        motorcycle = payload ?: Motorcycle.empty()

        // Track Event
        if (motorcycle.objectId.isBlank()) {
            analytics.trackEvent(AnalyticsService.Events.NEW_MOTORCYCLE)
        } else {
            analytics.trackEvent(AnalyticsService.Events.EDIT_MOTORCYCLE, mapOf("Brand" to motorcycle.brand, "Model" to motorcycle.model, "Year" to motorcycle.year.toString()))
        }

        // Finish up
        data.postValue(motorcycle)
    }

    override fun saveMotorcycle() {
        val result = webservice.saveMotorcycle(motorcycle)
        if (!result.success) {
            println("EditMotorcycleViewModel.saveMotorcycle FAILED!")
        }
    }
}