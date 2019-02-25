package se.idoapps.kotlinmotorcycles.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.service.WebService
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface

class WebServiceTests {
    private lateinit var webservice: WebServiceInterface

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initTests() {
        webservice = WebService()
    }

    @Test
    fun `When fetching motorcycles the endpoint should return success`() {
        runBlocking {
            // ARRANGE

            // ACT
            val response =  webservice.getMotorcycles()

            // ASSERT
            assert(response.success)
            assert(response.data.isNotEmpty())
        }
    }

    @Test
    fun completeCRUD() {
        runBlocking {
            // ACT - Get All Motorcycles
            val motorcyclesResponse =  webservice.getMotorcycles()

            // ASSERT - Get All Motorcycles
            assert(motorcyclesResponse.success)
            assert(motorcyclesResponse.data.isNotEmpty())

            val numberOfMotorcyclesAtStart = motorcyclesResponse.data.size

            // ACT - Create a motorcycle
            val newMotorcycleResponse = webservice.saveMotorcycle(Motorcycle("", "Kawasaki", "Test Race", 2000))

            // ASSERT - Create a motorcycle
            assert(newMotorcycleResponse.success)
            assert(newMotorcycleResponse.data?.objectId?.isNotBlank()!!)

            val newMotorcycleId = newMotorcycleResponse.data?.objectId!!

            // ACT - Get All Motorcycles After Create
            val motorcyclesAfterCreateResponse =  webservice.getMotorcycles()

            // ASSERT - Get All Motorcycles After Create
            assert(motorcyclesAfterCreateResponse.success)
            assert(motorcyclesAfterCreateResponse.data.isNotEmpty())
            assertEquals(numberOfMotorcyclesAtStart + 1, motorcyclesAfterCreateResponse.data.size)

            // ACT - Get One Motorcycle
            val motorcycleResponse = webservice.getMotorcycle(newMotorcycleId)

            // ASSERT - Get One Motorcycle
            assert(motorcycleResponse.success)
            assertEquals(newMotorcycleId, motorcycleResponse.data?.objectId)

            // ACT - Delete Motorcycle
            val deleteResponse = webservice.deleteMotorcycle(newMotorcycleId)

            // ASSERT - Delete Motorcycle
            assert(deleteResponse.success)

            // ACT - Get All Motorcycles After Delete
            val motorcyclesAfterDeleteResponse =  webservice.getMotorcycles()

            // ASSERT - Get All Motorcycles After Delete
            assert(motorcyclesAfterDeleteResponse.success)
            assert(motorcyclesAfterDeleteResponse.data.isNotEmpty())
            assertEquals(numberOfMotorcyclesAtStart, motorcyclesAfterDeleteResponse.data.size)

            // ACT - Get One Motorcycle After Delete -> Should Fail
            val motorcycleAfterDeleteResponse = webservice.getMotorcycle(newMotorcycleId)

            // ASSERT - Get One Motorcycle After Delete -> Should Fail
            assert(!motorcycleAfterDeleteResponse.success)
            assertNull(motorcycleAfterDeleteResponse.data)
        }
    }
}