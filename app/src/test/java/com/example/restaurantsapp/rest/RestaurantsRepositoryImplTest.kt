package com.example.restaurantsapp.rest

import com.example.restaurantsapp.database.LocalRepository
import com.example.restaurantsapp.database.LocalRepositoryImpl
import com.example.restaurantsapp.database.RestaurantsDAO
import com.example.restaurantsapp.model.Business
import com.example.restaurantsapp.model.YelpResponse
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.model.domain.ReviewDomain
import com.example.restaurantsapp.model.domain.restaurant.RestaurantResponse
import com.example.restaurantsapp.model.domain.reviews.ReviewsResponse
import com.example.restaurantsapp.utils.UIState
import io.mockk.MockKSettings.relaxed
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock


internal class RestaurantsRepositoryImplTest {

    private lateinit var testObject: RestaurantsRepository
    private var localRepository = mockk<LocalRepository>(relaxed = true)
    private val mockServiceApi = mockk<RestaurantsApi>(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)


    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)
        testObject = RestaurantsRepositoryImpl(mockServiceApi, localRepository)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

//    @Test
//    fun `get all restaurants when server returns a success response`() {
//        //ASSIGN
//        coEvery {
//            mockServiceApi.getHotNewRestaurants(
//                currentLoc.currentLatitude,
//                currentLoc.currentLongitude
//            )
//        } returns mockk {
//            every { isSuccessful } returns true
//            every { body() } returns YelpResponse(businesses = listOf(mockk(), mockk()))
//        }
//
//
//        //ACTION
//        var state: UIState<List<RestaurantDomain>> = UIState.LOADING
//        val job = testScope.launch {
//            testObject.getHotNewRestaurants().collect {
//                state = it
//            }
//        }
//
//        //ASSESS
//        assertEquals((state as UIState.SUCCESS<List<RestaurantDomain>>).response.size,2)
////        assert(state is UIState.SUCCESS)
//
//        job.cancel()
//
//    }

    @Test
    fun `get a restaurant when server returns success response`() {
        //ASSIGN
        coEvery {
            mockServiceApi.getRestaurantByID(
                id = "123"
            )
        } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns Business(name = "abc", imageUrl = "abc", id = "abc")
        }

        //ACTION
        var state: UIState<RestaurantDomain> = UIState.LOADING
        val job = testScope.launch {
            testObject.getRestaurantByID(id = "123").collect {
                state = it
            }
        }

//        ASSESS
        assertEquals((state as UIState.SUCCESS).response.name, "abc")

        job.cancel()

        //ASSESS
    }

    @Test
    fun `get reviews when server returns success response`() {
        //ASSIGN
        coEvery {
            mockServiceApi.getReviewsByID(
                id = "123"
            )
        } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns ReviewsResponse(reviews = listOf(mockk(), mockk()), total=3)
        }

        //ACTION
        var state: UIState<List<ReviewDomain>> = UIState.LOADING
        val job = testScope.launch {
            testObject.getReviewsByID(id = "123").collect {
                state = it
            }
        }

        // ASSERTION
//        assert((state as UIState.SUCCESS).response
        assertEquals((state as UIState.SUCCESS<List<ReviewDomain>>).response.size, 2)

        job.cancel()

    }
}
