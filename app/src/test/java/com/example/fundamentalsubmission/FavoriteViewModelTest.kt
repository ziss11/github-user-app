package com.example.fundamentalsubmission

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fundamentalsubmission.data.repositories.UserRepository
import com.example.fundamentalsubmission.dummy_data.DummyObject
import com.example.fundamentalsubmission.presentation.viewmodels.FavoriteViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class FavoriteViewModelTest {
    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    private lateinit var userRepository: UserRepository
    private lateinit var favoriteViewModel: FavoriteViewModel

    @Before
    fun setup() {
        userRepository = mock(UserRepository::class.java)
        favoriteViewModel = FavoriteViewModel(userRepository)
    }

    @Test
    fun testMockFetchFavoriteUsers() {
        `when`(userRepository.fetchFavoriteUsers()).thenReturn(DummyObject.dummyListUserEntity)
        val result = favoriteViewModel.fetchFavoriteUsers()
        verify(userRepository).fetchFavoriteUsers()
        assertEquals(DummyObject.dummyListUserEntity, result)
    }
}