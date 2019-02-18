package se.idoapps.kotlinmotorcycles.service

import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import com.github.kittinunf.result.Result
import retrofit2.HttpException
import se.idoapps.kotlinmotorcycles.common.fromJson
import se.idoapps.kotlinmotorcycles.model.MotorcyclesContainer

class WebService : WebServiceInterface {
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

    companion object {
        private const val BASE_URL = "https://api.backendless.com/8497684E-2B7B-1E53-FF7C-3C2B0CE99700/282CDAE9-FE22-748C-FF61-DC3168DB0D00/"

        private fun getMotorcyclesUrl() = "${BASE_URL}data/Motorcycles"
    }
}