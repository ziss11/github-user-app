package com.example.fundamentalsubmission.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.fundamentalsubmission.Injection.provideSettingDataSources
import com.example.fundamentalsubmission.data.datasources.SettingsDataSource

class SettingRepository private constructor(private val dataSource: SettingsDataSource) {
    fun getThemeSetting() = dataSource.getThemeSetting()

    suspend fun saveThemeSetting(themeMode: Int) = dataSource.saveThemeSetting(themeMode)

    companion object {
        private var instance: SettingRepository? = null

        fun getInstance(dataStore: DataStore<Preferences>) = instance ?: synchronized(this) {
            instance ?: SettingRepository(
                provideSettingDataSources(dataStore)
            )
        }.also { instance = it }
    }
}