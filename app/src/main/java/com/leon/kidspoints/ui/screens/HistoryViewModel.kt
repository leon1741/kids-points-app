package com.leon.kidspoints.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leon.kidspoints.data.repository.PointsRepository
import com.leon.kidspoints.domain.model.PointRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val pointsRepository: PointsRepository
) : ViewModel() {

    val allRecords: StateFlow<List<PointRecord>> = pointsRepository.allRecords
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
