package com.example.restaurantsapp.rest

import com.example.restaurantsapp.database.LocalRepository
import com.example.restaurantsapp.model.Business
import com.example.restaurantsapp.model.YelpResponse
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.model.domain.ReviewDomain
import com.example.restaurantsapp.model.domain.reviews.ReviewsResponse
import com.example.restaurantsapp.utils.UIState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


//@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
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

    @Test
    fun `get all hot and new restaurants when server has a list of items returns a success state`() {
       // ASSIGNMENT
        coEvery { mockServiceApi.getHotNewRestaurants(0.0, 0.0) } returns mockk  {
            every { isSuccessful } returns true
            every { body() } returns YelpResponse(listOf(
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true)
            )
            )
        }
        val states =  mutableListOf<UIState<List<RestaurantDomain>>>()

        // ACTION
        val job = testScope.launch {
            testObject.getHotNewRestaurants(0.0, 0.0).collect {
                states.add(it)
            }
        }

        // ASSERTION
        assertEquals(2, states.size)
        val success = (states[1]  as UIState.SUCCESS).response
        assertEquals(3, success.size)

        coVerify { mockServiceApi.getHotNewRestaurants(0.0,0.0) }

        job.cancel()
    }

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

//    class SandwichTest
    @Test
    fun `get reviews when server returns success response`() {
        //ASSIGN
        coEvery { mockServiceApi.getReviewsByID(id = "123") } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns ReviewsResponse(
                reviews = listOf(
                    mockk(relaxed = true),
                    mockk(relaxed = true),
                    mockk(relaxed = true)
                ),
                total=2
            )
        }

        //ACTION
    val states =  mutableListOf<UIState<List<ReviewDomain>>>()

    val job = testScope.launch {
        testObject.getReviewsByID(id = "123").collect {
            states.add( it)
        }
    }

    // ASSERTION
    assertEquals(2, states.size)
    assertEquals((states[1] as UIState.SUCCESS<List<ReviewDomain>>).response.size, 3)

    job.cancel()

    }
}

//  mokito can't mock static or private method
