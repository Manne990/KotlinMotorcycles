package se.idoapps.kotlinmotorcycles.unit.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
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

        webservice = mock {
            onBlocking { getMotorcycles() }.doReturn(motorcyclesResult)
        }

        analytics = mock()

        viewModel = MotorcyclesViewModel(webservice, analytics)
    }

    @Test
    fun `When fetching motorcycles then two motorcycles shall be returned`() {
        runBlocking {
            // ARRANGE

            // ACT
            viewModel.loadMotorcyclesAsync()

            // ASSERT
            assertEquals(2, viewModel.motorcycles.value?.size)
        }
    }
}