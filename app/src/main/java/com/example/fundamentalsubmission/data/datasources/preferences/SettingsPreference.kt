package com.example.fundamentalsubmission.data.datasources.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getTheme(): Flow<Int> {
        return dataStore.data.map { preference ->
            preference[themeKey] ?: 0
        }
    }

    suspend fun saveThemeSetting(themeMode: Int) {
        dataStore.edit { preferences ->
            preferences[themeKey] = themeMode
        }
    }

    companion object {
        private val themeKey = intPreferencesKey("theme_setting")
        private var instance: SettingsPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>) = instance ?: synchronized(this) {
            instance ?: SettingsPreference(dataStore)
        }.also { instance = it }
    }
}