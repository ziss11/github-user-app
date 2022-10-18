package com.example.fundamentalsubmission.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fundamentalsubmission.data.repositories.UserRepository
import com.example.fundamentalsubmission.presentation.viewmodels.dummy_data.DummyObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class DetailViewModelTest {
    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    private lateinit var userRepository: UserRepository
    private lateinit var detailViewModel: DetailViewModel

    val username = "ziszz"

    @Before
    fun setup() {
        userRepository = mock(UserRepository::class.java)
        detailViewModel = DetailViewModel(userRepository)
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
        `when`(userRepository.add2Favorite(DummyObject.dummyUserModel)).thenReturn(DummyObject.dummyAddFavoriteResponse)
        val result = detailViewModel.addToFavorite(DummyObject.dummyUserModel)
        verify(userRepository).add2Favorite(DummyObject.dummyUserModel)
        assertEquals(DummyObject.dummyAddFavoriteResponse, result)
    }

    @Test
    fun testMockRemoveFromFavorite() {
        `when`(userRepository.removeFromFavorite(username)).thenReturn(DummyObject.dummyDeleteFavoriteResponse)
        val result = detailViewModel.removeFromFavorite(username)
        verify(userRepository).removeFromFavorite(username)
        assertEquals(DummyObject.dummyDeleteFavoriteResponse, result)
    }
}