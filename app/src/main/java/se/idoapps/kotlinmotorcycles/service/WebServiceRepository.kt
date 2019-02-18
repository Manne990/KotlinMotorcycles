package se.idoapps.kotlinmotorcycles.service

import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import com.github.kittinunf.result.Result
import se.idoapps.kotlinmotorcycles.common.fromJson
import se.idoapps.kotlinmotorcycles.model.MotorcyclesContainer

class WebServiceRepository : WebServiceInterface {
    override fun getMotorcycles(): MotorcyclesContainer {
        val endPoint = getMotorcyclesUrl()

        val (_, _, result) = endPoint
            .httpGet()
            .responseString()

        return when(result)
        {
            is Result.Success -> {
                MotorcyclesContainer(Gson().fromJson<List<Motorcycle>>(result.value))
            }
            is Result.Failure -> {
                MotorcyclesContainer()
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://api.backendless.com/8497684E-2B7B-1E53-FF7C-3C2B0CE99700/282CDAE9-FE22-748C-FF61-DC3168DB0D00/"

        private fun getMotorcyclesUrl() = "${BASE_URL}data/Motorcycles"
    }
}