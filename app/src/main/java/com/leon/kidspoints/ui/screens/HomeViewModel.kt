package com.leon.kidspoints.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leon.kidspoints.data.repository.PointsRepository
import com.leon.kidspoints.data.repository.RulesRepository
import com.leon.kidspoints.domain.model.Rule
import com.leon.kidspoints.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val rulesRepository: RulesRepository,
    private val pointsRepository: PointsRepository
) : ViewModel() {

    val activeRules: StateFlow<List<Rule>> = rulesRepository.activeRules
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalPoints: StateFlow<Int> = flow {
        emit(pointsRepository.getTotalPoints())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    fun onCompleteRule(ruleId: Int) {
        viewModelScope.launch {
            pointsRepository.addCompleteRecord(ruleId)
        }
    }

    fun onViolationRule(ruleId: Int) {
        viewModelScope.launch {
            pointsRepository.addViolationRecord(ruleId)
        }
    }

    fun toggleRuleActive(id: Int, isActive: Boolean) {
        viewModelScope.launch {
            rulesRepository.toggleRuleActive(id, isActive)
        }
    }
}
