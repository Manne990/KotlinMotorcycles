package se.idoapps.kotlinmotorcycles.unit.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import se.idoapps.kotlinmotorcycles.model.*
import se.idoapps.kotlinmotorcycles.service.AnalyticsServiceInterface
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import se.idoapps.kotlinmotorcycles.viewmodel.*

class EditMotorcycleViewModelTests {
    private lateinit var viewModel: EditMotorcycleViewModelInterface
    private lateinit var webservice: WebServiceInterface
    private lateinit var analytics: AnalyticsServiceInterface
    private lateinit var motorcycleResult: MotorcycleContainer

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initTests() {
        motorcycleResult = MotorcycleContainer(
            Motorcycle("1", "Yamaha", "R1", 2007),
            true
        )

        webservice = mock {
            onBlocking{ getMotorcycle(any()) }.doReturn(motorcycleResult)
        }
        analytics = mock()
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