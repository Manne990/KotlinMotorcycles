package se.idoapps.kotlinmotorcycles.service

import com.github.kittinunf.fuel.*
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import retrofit2.HttpException
import se.idoapps.kotlinmotorcycles.application.Configuration
import se.idoapps.kotlinmotorcycles.common.fromJson
import se.idoapps.kotlinmotorcycles.model.*
import java.nio.charset.Charset

class  WebService : WebServiceInterface {
    // Public Functions
    override suspend fun getMotorcycles(): MotorcyclesContainer {
        try {
            val endPoint = allMotorcyclesUrl()

            val (_, _, result) = endPoint
                .httpGet()
                .responseString()

            return when(result)
            {
                is Result.Success -> {
                    MotorcyclesContainer(Gson().fromJson<List<Motorcycle>>(result.value), true)
                }

                is Result.Failure -> {
                    MotorcyclesContainer(listOf(), false)
                }
            }
        } catch (e: HttpException) {
            println(e.code())
            println(e.message)
            return MotorcyclesContainer(listOf(), false)
        } catch (e: Throwable) {
            println(e.message)
            return MotorcyclesContainer(listOf(), false)
        }
    }

    override suspend fun getMotorcycle(id: String): MotorcycleContainer {
        try {
            val endPoint = oneMotorcycleUrl(id)

            val (_, _, result) = endPoint
                .httpGet()
                .responseString()

            return when(result)
            {
                is Result.Success -> {
                    MotorcycleContainer(Gson().fromJson<Motorcycle>(result.value), true)
                }

                is Result.Failure -> {
                    MotorcycleContainer(null, false)
                }
            }
        } catch (e: HttpException) {
            println(e.code())
            println(e.message)
            return MotorcycleContainer(null, false)
        } catch (e: Throwable) {
            println(e.message)
            return MotorcycleContainer(null, false)
        }
    }

    override suspend fun saveMotorcycle(motorcycle: Motorcycle): MotorcycleContainer {
        if (motorcycle.objectId.isEmpty()) {
            return createNewMotorcycle(motorcycle)
        }

        return updateMotorcycle(motorcycle)
    }

    override suspend fun deleteMotorcycle(id: String): EmptyContainer {
        try {
            val endPoint = oneMotorcycleUrl(id)

            val (_, _, result) = endPoint
                .httpDelete()
                .responseString()

            return when(result)
            {
                is Result.Success -> {
                    EmptyContainer(true)
                }

                is Result.Failure -> {
                    EmptyContainer(false)
                }
            }
        } catch (e: HttpException) {
            println(e.code())
            println(e.message)
            return EmptyContainer(false)
        } catch (e: Throwable) {
            println(e.message)
            return EmptyContainer(false)
        }
    }


    // Private Functions
    private fun createNewMotorcycle(motorcycle: Motorcycle): MotorcycleContainer {
        try {
            val endPoint = allMotorcyclesUrl()

            val (_, _, result) = endPoint
                .httpPost()
                .header("Content-Type" to "application/json")
                .body(Gson().toJson(motorcycle), Charset.forName("UTF-8"))
                .responseString()

            return when(result)
            {
                is Result.Success -> {
                    MotorcycleContainer(Gson().fromJson<Motorcycle>(result.value), true)
                }

                is Result.Failure -> {
                    MotorcycleContainer(null, false)
                }
            }
        } catch (e: HttpException) {
            println(e.code())
            println(e.message)
            return MotorcycleContainer(null, false)
        } catch (e: Throwable) {
            println(e.message)
            return MotorcycleContainer(null, false)
        }
    }

    private fun updateMotorcycle(motorcycle: Motorcycle): MotorcycleContainer {
        try {
            val endPoint = oneMotorcycleUrl(motorcycle.objectId)

            val (_, _, result) = endPoint
                .httpPut()
                .header("Content-Type" to "application/json")
                .body(Gson().toJson(motorcycle), Charset.forName("UTF-8"))
                .responseString()

            return when(result)
            {
                is Result.Success -> {
                    MotorcycleContainer(motorcycle,true)
                }

                is Result.Failure -> {
                    MotorcycleContainer(motorcycle,false)
                }
            }
        } catch (e: HttpException) {
            println(e.code())
            println(e.message)
            return MotorcycleContainer(motorcycle,false)
        } catch (e: Throwable) {
            println(e.message)
            return MotorcycleContainer(motorcycle,false)
        }
    }

    companion object {
        private fun allMotorcyclesUrl() = "${Configuration.BASE_URL}data/Motorcycles"
        private fun oneMotorcycleUrl(id: String) = "${Configuration.BASE_URL}data/Motorcycles/$id"
    }
}