package se.idoapps.kotlinmotorcycles.service

import se.idoapps.kotlinmotorcycles.model.*

interface WebServiceInterface {
    suspend fun getMotorcycles() : MotorcyclesContainer
    suspend fun getMotorcycle(id: String) : MotorcycleContainer
    suspend fun saveMotorcycle(motorcycle: Motorcycle) : MotorcycleContainer
    suspend fun deleteMotorcycle(id: String): EmptyContainer
}