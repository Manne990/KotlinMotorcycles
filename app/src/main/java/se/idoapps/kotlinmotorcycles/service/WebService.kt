package se.idoapps.kotlinmotorcycles.service

import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import com.github.kittinunf.result.Result
import retrofit2.HttpException
import se.idoapps.kotlinmotorcycles.application.Configuration
import se.idoapps.kotlinmotorcycles.common.fromJson
import se.idoapps.kotlinmotorcycles.model.MotorcycleContainer
import se.idoapps.kotlinmotorcycles.model.MotorcyclesContainer

class  WebService : WebServiceInterface {
    override fun getMotorcycles(): MotorcyclesContainer {
        try {
            val endPoint = getMotorcyclesUrl()

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

    override fun getMotorcycle(id: String): MotorcycleContainer {
        try {
            val endPoint = getMotorcyclesUrl() + "/" + id

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

    companion object {
        private fun getMotorcyclesUrl() = "${Configuration.BASE_URL}data/Motorcycles"
    }
}