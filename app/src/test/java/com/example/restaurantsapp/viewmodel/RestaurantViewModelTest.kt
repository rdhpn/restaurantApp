package com.example.restaurantsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.example.restaurantsapp.database.LocalRepository
import com.example.restaurantsapp.model.Business
import com.example.restaurantsapp.model.YelpResponse
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.rest.RestaurantsRepository
import com.example.restaurantsapp.rest.RestaurantsRepositoryImpl
import com.example.restaurantsapp.utils.UIState
import com.example.restaurantsapp.utils.ViewType
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class RestaurantViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var testObject: RestaurantViewModel
    private val testRepository: RestaurantsRepository = mockk(relaxed = true)
    private val testLocalRepository: LocalRepository = mockk(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        //statements here are used throughout the test.  If we only need to instantiate once,
        // just like when there is an init function in the original file. it has to go to
        // test fun body
        Dispatchers.setMain(testDispatcher)
        testObject = RestaurantViewModel(testRepository, testDispatcher, testLocalRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `get restaurant when repository retrieves a list of restaurants returns a success state`() {
        every { testRepository.getHotNewRestaurants(0.1, 0.2) } returns flowOf(
            UIState.SUCCESS(listOf(mockk(), mockk(), mockk()))
        )

        testObject.restaurantsByLocation.observeForever {
        when (it) {
            is UIState.LOADING -> {}
            is UIState.SUCCESS<List<RestaurantDomain>> -> {
                Assert.assertEquals(3, it.response.size)
            }
            is UIState.ERROR -> {
            }
            }
        }

        testObject.getRestaurantsByLocation(0.1, 0.2)

    }
}
