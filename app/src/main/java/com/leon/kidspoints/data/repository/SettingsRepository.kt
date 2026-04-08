package com.leon.kidspoints.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepository(private val context: Context) {

    companion object {
        val PARENT_PASSWORD_KEY = stringPreferencesKey("parent_password")
        val PASSWORD_SET_KEY = stringPreferencesKey("password_set")
    }

    val parentPasswordFlow: Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[PARENT_PASSWORD_KEY]
        }

    val isPasswordSetFlow: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[PASSWORD_SET_KEY] == "true"
        }

    suspend fun setParentPassword(password: String) {
        context.dataStore.edit { preferences ->
            preferences[PARENT_PASSWORD_KEY] = password
            preferences[PASSWORD_SET_KEY] = "true"
        }
    }

    suspend fun verifyPassword(password: String): Boolean {
        val storedPassword = context.dataStore.data.map {
            it[PARENT_PASSWORD_KEY]
        }.first()
        return storedPassword == password
    }

    suspend fun clearPassword() {
        context.dataStore.edit { preferences ->
            preferences.remove(PARENT_PASSWORD_KEY)
            preferences.remove(PASSWORD_SET_KEY)
        }
    }
}
