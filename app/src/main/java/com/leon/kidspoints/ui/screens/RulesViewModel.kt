package com.leon.kidspoints.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leon.kidspoints.data.repository.RulesRepository
import com.leon.kidspoints.data.repository.SettingsRepository
import com.leon.kidspoints.domain.model.Rule
import com.leon.kidspoints.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RulesViewModel @Inject constructor(
    private val rulesRepository: RulesRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val allRules: StateFlow<List<Rule>> = rulesRepository.allRules
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val categories: StateFlow<List<String>> = rulesRepository.allCategories
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Category.entries.map { it.displayName }
        )

    private val _isParentMode = MutableStateFlow(false)
    val isParentMode: StateFlow<Boolean> = _isParentMode.asStateFlow()

    fun addRule(rule: Rule) {
        viewModelScope.launch {
            rulesRepository.insertRule(rule)
        }
    }

    fun updateRule(rule: Rule) {
        viewModelScope.launch {
            rulesRepository.updateRule(rule)
        }
    }

    fun deleteRule(rule: Rule) {
        viewModelScope.launch {
            rulesRepository.deleteRule(rule)
        }
    }

    fun toggleRuleActive(id: Int, isActive: Boolean) {
        viewModelScope.launch {
            rulesRepository.toggleRuleActive(id, isActive)
        }
    }

    fun enterParentMode(isVerified: Boolean) {
        _isParentMode.value = isVerified
    }

    suspend fun verifyPassword(password: String): Boolean {
        return settingsRepository.verifyPassword(password)
    }
}
