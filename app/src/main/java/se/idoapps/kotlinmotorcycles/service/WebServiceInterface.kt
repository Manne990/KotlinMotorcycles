package se.idoapps.kotlinmotorcycles.service

import se.idoapps.kotlinmotorcycles.model.*

interface WebServiceInterface {
    fun getMotorcycles() : MotorcyclesContainer
    fun getMotorcycle(id: String) : MotorcycleContainer
}