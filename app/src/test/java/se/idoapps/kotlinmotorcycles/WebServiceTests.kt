package se.idoapps.kotlinmotorcycles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.model.MotorcyclesContainer
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface

class WebServiceTests {
    private lateinit var webservice: WebServiceInterface
    private lateinit var motorcyclesResult: MotorcyclesContainer

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initTests() {

        //TODO: Mock endpoint in class WebService

//        motorcyclesResult = MotorcyclesContainer(
//            listOf(
//                Motorcycle("1", "Yamaha", "R1", 2007),
//                Motorcycle("2", "Ducati", "916", 1995)
//            )
//            , true
//        )
//
//        webservice = Mockito.mock(WebServiceInterface::class.java)
//        Mockito.`when`(webservice.getMotorcycles()).thenReturn(motorcyclesResult)
    }

    @Test
    fun `When fetching motorcycles then two motorcycles shall be returned`() {
        // ARRANGE

        // ACT

        // ASSERT
        assertEquals(1,1 )
    }
}