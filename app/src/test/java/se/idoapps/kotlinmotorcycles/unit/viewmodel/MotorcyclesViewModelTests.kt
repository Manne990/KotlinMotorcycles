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

class MotorcyclesViewModelTests {
    private lateinit var viewModel: MotorcyclesViewModelInterface
    private lateinit var webservice: WebServiceInterface
    private lateinit var analytics: AnalyticsServiceInterface
    private lateinit var motorcyclesResult: MotorcyclesContainer

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initTests() {
        motorcyclesResult = MotorcyclesContainer(
            listOf(
                Motorcycle("1", "Yamaha", "R1", 2007),
                Motorcycle("2", "Ducati", "916", 1995)
            )
            , true
        )

        webservice = Mockito.mock(WebServiceInterface::class.java)
        analytics = Mockito.mock(AnalyticsServiceInterface::class.java)

        Mockito.`when`(webservice.getMotorcycles()).thenReturn(motorcyclesResult)

        viewModel = MotorcyclesViewModel(webservice, analytics)
    }

    @Test
    fun `When fetching motorcycles then two motorcycles shall be returned`() {
        // ARRANGE

        // ACT
        viewModel.loadMotorcycles()

        // ASSERT
        assertEquals(2, viewModel.motorcycles.value?.size)
    }
}