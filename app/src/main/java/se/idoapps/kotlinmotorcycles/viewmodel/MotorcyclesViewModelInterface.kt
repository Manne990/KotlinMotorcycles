package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.LiveData
import se.idoapps.kotlinmotorcycles.model.Motorcycle

interface MotorcyclesViewModelInterface {
    val motorcycles: LiveData<List<Motorcycle>>

    suspend fun loadMotorcyclesAsync()
    suspend fun deleteMotorcycleAsync(motorcycle: Motorcycle)
}