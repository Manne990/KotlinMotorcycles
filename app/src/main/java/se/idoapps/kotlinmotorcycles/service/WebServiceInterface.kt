package se.idoapps.kotlinmotorcycles.service

import se.idoapps.kotlinmotorcycles.model.MotorcyclesContainer

interface WebServiceInterface {
    fun getMotorcycles() : MotorcyclesContainer
}