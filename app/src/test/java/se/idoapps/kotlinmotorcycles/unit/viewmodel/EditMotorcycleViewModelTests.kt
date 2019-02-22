package se.idoapps.kotlinmotorcycles.unit.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import se.idoapps.kotlinmotorcycles.model.*
import se.idoapps.kotlinmotorcycles.service.AnalyticsServiceInterface
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import se.idoapps.kotlinmotorcycles.viewmodel.*

class EditMotorcycleViewModelTests {
    private lateinit var viewModel: EditMotorcycleViewModelInterface
    private lateinit var webservice: WebServiceInterface
    private lateinit var analytics: AnalyticsServiceInterface

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initTests() {
        webservice = Mockito.mock(WebServiceInterface::class.java)
        analytics = Mockito.mock(AnalyticsServiceInterface::class.java)
        viewModel = EditMotorcycleViewModel(webservice, analytics)
    }

    @Test
    fun `When init with payload viewmodel should be updated`() {
        // ARRANGE
        val payload = Motorcycle("1", "Yamaha", "R1", 2007)

        // ACT
        viewModel.initWithPayload(payload)

        // ASSERT
        assertEquals(payload.objectId, viewModel.motorcycle.objectId)
    }
}