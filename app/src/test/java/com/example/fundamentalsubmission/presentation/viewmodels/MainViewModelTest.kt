package com.example.fundamentalsubmission.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fundamentalsubmission.data.repositories.SettingRepository
import com.example.fundamentalsubmission.data.repositories.UserRepository
import com.example.fundamentalsubmission.presentation.viewmodels.dummy_data.DummyObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
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
class MainViewModelTest {
    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var settingRepository: SettingRepository

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        userRepository = mock(UserRepository::class.java)
        settingRepository = mock(SettingRepository::class.java)
        mainViewModel = MainViewModel(userRepository, settingRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testMockFetchUsers() {
        `when`(userRepository.fetchUsers()).thenReturn(DummyObject.dummyListUserModel)
        val result = mainViewModel.fetchUsers()
        verify(userRepository).fetchUsers()
        assertEquals(DummyObject.dummyListUserModel, result)
    }

    @Test
    fun testMockFetchSearchedUsers() {
        val username = "ziszz"

        `when`(userRepository.fetchSearchedUsers(username)).thenReturn(DummyObject.dummyListUserModel)
        val result = mainViewModel.fetchSearchedUsers(username)
        verify(userRepository).fetchSearchedUsers(username)
        assertEquals(DummyObject.dummyListUserModel, result)
    }

    @Test
    fun testMockGetThemeSetting() {
        `when`(settingRepository.getThemeSetting()).thenReturn(DummyObject.dummyThemeMode)
        val result = mainViewModel.getThemeSetting()
        verify(settingRepository).getThemeSetting()
        assertEquals(DummyObject.dummyThemeMode, result)
    }

    @Test
    fun testMockSaveThemeSetting() {
        mainViewModel.saveThemeSetting(1)
        runBlocking {
            launch {
                verify(settingRepository).saveThemeSetting(1)
            }
        }
    }
}