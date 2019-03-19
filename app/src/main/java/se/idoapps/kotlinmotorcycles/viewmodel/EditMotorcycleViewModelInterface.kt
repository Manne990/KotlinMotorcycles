package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.LiveData
import se.idoapps.kotlinmotorcycles.model.Motorcycle

interface EditMotorcycleViewModelInterface {
    var motorcycle: Motorcycle
    val data: LiveData<Motorcycle>

    fun initWithPayload(payload: Motorcycle?)
    suspend fun saveMotorcycleAsync()
}