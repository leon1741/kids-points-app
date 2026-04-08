package com.leon.kidspoints.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leon.kidspoints.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val isPasswordSet: StateFlow<Boolean> = settingsRepository.isPasswordSetFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun setPassword(password: String) {
        viewModelScope.launch {
            settingsRepository.setParentPassword(password)
        }
    }

    suspend fun verifyPassword(password: String): Boolean {
        return settingsRepository.verifyPassword(password)
    }

    fun clearPassword() {
        viewModelScope.launch {
            settingsRepository.clearPassword()
        }
    }
}

