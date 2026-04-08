package com.leon.kidspoints.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leon.kidspoints.data.repository.GiftsRepository
import com.leon.kidspoints.data.repository.PointsRepository
import com.leon.kidspoints.domain.model.Gift
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiftsViewModel @Inject constructor(
    private val giftsRepository: GiftsRepository,
    private val pointsRepository: PointsRepository
) : ViewModel() {

    val availableGifts: StateFlow<List<Gift>> = giftsRepository.availableGifts
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

    fun addGift(gift: Gift) {
        viewModelScope.launch {
            giftsRepository.insertGift(gift)
        }
    }

    fun updateGift(gift: Gift) {
        viewModelScope.launch {
            giftsRepository.updateGift(gift)
        }
    }

    fun deleteGift(gift: Gift) {
        viewModelScope.launch {
            giftsRepository.deleteGift(gift)
        }
    }

    fun redeemGift(gift: Gift) {
        viewModelScope.launch {
            pointsRepository.addRedemptionRecord(gift.id, gift.name, gift.cost)
        }
    }
}
