package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.service.*
import javax.inject.Inject

class EditMotorcycleViewModel @Inject constructor(private val webservice: WebServiceInterface, private val analytics: AnalyticsServiceInterface) : ViewModel(), EditMotorcycleViewModelInterface {
    // Private Members
    private val _data: MutableLiveData<Motorcycle> = MutableLiveData()

    // Public Properties
    override val data: LiveData<Motorcycle> = _data
    override var motorcycle: Motorcycle = Motorcycle.empty()

    // Public Functions
    override fun initWithPayload(payload: Motorcycle?) {
        // Init
        motorcycle = payload ?: Motorcycle.empty()

        // Track Event
        if (motorcycle.objectId.isBlank()) {
            analytics.trackEvent(AnalyticsServiceAbstractions.Events.NEW_MOTORCYCLE)
        } else {
            analytics.trackEvent(AnalyticsServiceAbstractions.Events.EDIT_MOTORCYCLE, mapOf("Brand" to motorcycle.brand, "Model" to motorcycle.model, "Year" to motorcycle.year.toString()))
        }

        // Finish up
        _data.postValue(motorcycle)
    }

    override suspend fun saveMotorcycleAsync() {
        val result = webservice.saveMotorcycle(motorcycle)
        if (!result.success) {
            println("EditMotorcycleViewModel.saveMotorcycle FAILED!")
        }
    }
}