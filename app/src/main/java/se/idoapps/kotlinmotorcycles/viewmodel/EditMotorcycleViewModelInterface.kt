package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.MutableLiveData
import se.idoapps.kotlinmotorcycles.model.Motorcycle

interface EditMotorcycleViewModelInterface {
    var motorcycle: Motorcycle
    val data: MutableLiveData<Motorcycle>

    fun initWithPayload(payload: Motorcycle?)
    fun saveMotorcycle()
}