package se.idoapps.kotlinmotorcycles.model

data class MotorcyclesContainer(val data: List<Motorcycle> = listOf(), override val success: Boolean) : ContainerBase()