package se.idoapps.kotlinmotorcycles.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
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
        // ARRANGE

        // ACT
        val response =  webservice.getMotorcycles()

        // ASSERT
        assertNotNull(response)
        assert(response.success)
    }
}