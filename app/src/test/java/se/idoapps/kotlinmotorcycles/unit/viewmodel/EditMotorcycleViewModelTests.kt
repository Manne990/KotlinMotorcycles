package se.idoapps.kotlinmotorcycles.unit.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import se.idoapps.kotlinmotorcycles.model.*
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import se.idoapps.kotlinmotorcycles.viewmodel.*

class EditMotorcycleViewModelTests {
    private lateinit var viewModel: EditMotorcycleViewModelInterface
    private lateinit var webservice: WebServiceInterface

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initTests() {
//        motorcyclesResult = MotorcyclesContainer(
//            listOf(
//                Motorcycle("1", "Yamaha", "R1", 2007),
//                Motorcycle("2", "Ducati", "916", 1995)
//            )
//            , true
//        )

        webservice = Mockito.mock(WebServiceInterface::class.java)
//        Mockito.`when`(webservice.getMotorcycles()).thenReturn(motorcyclesResult)

        viewModel = EditMotorcycleViewModel(webservice)
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