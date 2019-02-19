package se.idoapps.kotlinmotorcycles.model

data class MotorcyclesContainer(val data: List<Motorcycle> = listOf(), override val success: Boolean) : ContainerBase()
data class MotorcycleContainer(val data: Motorcycle?, override val success: Boolean) : ContainerBase()
data class EmptyContainer(override val success: Boolean) : ContainerBase()