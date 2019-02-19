package se.idoapps.kotlinmotorcycles.model

import java.io.Serializable

data class Motorcycle(var objectId: String, var brand: String, var model: String, var year: Int) : Serializable {
    companion object {
        fun empty(): Motorcycle {
            return Motorcycle("", "", "", 0)
        }
    }
}