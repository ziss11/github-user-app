package com.example.fundamentalsubmission

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fundamentalsubmission.data.repositories.UserRepository
import com.example.fundamentalsubmission.dummy_data.DummyObject
import com.example.fundamentalsubmission.presentation.viewmodels.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {
    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    private lateinit var userRepository: UserRepository
    private lateinit var detailViewModel: DetailViewModel

    private val username = "ziszz"

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        userRepository = mock(UserRepository::class.java)
        detailViewModel = DetailViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testMockGetDetailUser() {
        `when`(userRepository.getUserDetail(username)).thenReturn(DummyObject.dummyResultUserModel)
        val result = detailViewModel.getDetailUser(username)
        verify(userRepository).getUserDetail(username)
        assertEquals(DummyObject.dummyResultUserModel, result)
    }

    @Test
    fun testMockFetchUserFollowers() {
        `when`(userRepository.fetchUserFollowers(username)).thenReturn(DummyObject.dummyListUserModel)
        val result = detailViewModel.fetchUserFollowers(username)
        verify(userRepository).fetchUserFollowers(username)
        assertEquals(DummyObject.dummyListUserModel, result)
    }

    @Test
    fun testMockFetchUserFollowing() {
        `when`(userRepository.fetchUserFollowing(username)).thenReturn(DummyObject.dummyListUserModel)
        val result = detailViewModel.fetchUserFollowing(username)
        verify(userRepository).fetchUserFollowing(username)
        assertEquals(DummyObject.dummyListUserModel, result)
    }

    @Test
    fun testMockIsFavoriteUser() {
        `when`(userRepository.checkFavorite(username)).thenReturn(DummyObject.dummyIsFavoriteUser)
        val result = detailViewModel.isFavoriteUser(username)
        verify(userRepository).checkFavorite(username)
        assertEquals(DummyObject.dummyIsFavoriteUser, result)
    }

    @Test
    fun testMockAddToFavorite() {
        runBlocking {
            `when`(userRepository.add2Favorite(DummyObject.dummyUserModel)).thenReturn(Unit)
            detailViewModel.addToFavorite(DummyObject.dummyUserModel)
            verify(userRepository).add2Favorite(DummyObject.dummyUserModel)
        }
    }

    @Test
    fun testMockRemoveFromFavorite() {
        detailViewModel.removeFromFavorite(username)
        runBlocking {
            verify(userRepository).removeFromFavorite(username)
        }
    }
}