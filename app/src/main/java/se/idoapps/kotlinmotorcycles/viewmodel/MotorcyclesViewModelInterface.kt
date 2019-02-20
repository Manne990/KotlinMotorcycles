package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.MutableLiveData
import se.idoapps.kotlinmotorcycles.model.Motorcycle

interface MotorcyclesViewModelInterface {
    val motorcycles: MutableLiveData<List<Motorcycle>>

    fun loadMotorcycles()
    fun deleteMotorcycle(motorcycle: Motorcycle)
}