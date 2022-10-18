package com.example.fundamentalsubmission.data.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.fundamentalsubmission.Injection.provideSettingPref
import com.example.fundamentalsubmission.data.datasources.preferences.SettingsPreference

interface SettingsDataSource {
    fun getThemeSetting(): LiveData<Int>
    suspend fun saveThemeSetting(themeMode: Int)
}

class SettingsDataSourceImpl private constructor(private val pref: SettingsPreference) :
    SettingsDataSource {
    override fun getThemeSetting(): LiveData<Int> {
        return pref.getTheme().asLiveData()
    }

    override suspend fun saveThemeSetting(themeMode: Int) {
        pref.saveThemeSetting(themeMode)
    }

    companion object {
        private var instance: SettingsDataSourceImpl? = null

        fun getInstance(dataStore: DataStore<Preferences>) =
            instance ?: synchronized(this) {
                instance ?: SettingsDataSourceImpl(
                    provideSettingPref(dataStore)
                )
            }.also { instance = it }
    }
}