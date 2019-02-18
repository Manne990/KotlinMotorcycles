package se.idoapps.kotlinmotorcycles.viewmodel

import androidx.lifecycle.LiveData
import se.idoapps.kotlinmotorcycles.model.Motorcycle

interface MotorcyclesViewModelInterface {
    fun getMotorcycles(): LiveData<List<Motorcycle>>
}