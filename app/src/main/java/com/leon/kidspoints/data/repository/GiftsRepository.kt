package com.leon.kidspoints.data.repository

import com.leon.kidspoints.data.local.dao.GiftDao
import com.leon.kidspoints.data.local.entity.GiftEntity
import com.leon.kidspoints.domain.model.Gift
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GiftsRepository(private val giftDao: GiftDao) {

    val allGifts: Flow<List<Gift>> = giftDao.getAllGifts().map { entities ->
        entities.map { it.toDomain() }
    }

    val availableGifts: Flow<List<Gift>> = giftDao.getAvailableGifts().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun getGiftById(id: Int): Gift? {
        return giftDao.getGiftById(id)?.toDomain()
    }

    suspend fun insertGift(gift: Gift): Long {
        return giftDao.insertGift(gift.toEntity())
    }

    suspend fun updateGift(gift: Gift) {
        giftDao.updateGift(gift.toEntity())
    }

    suspend fun deleteGift(gift: Gift) {
        giftDao.deleteGift(gift.toEntity())
    }

    suspend fun toggleGiftAvailable(id: Int, isAvailable: Boolean) {
        giftDao.toggleGiftAvailable(id, isAvailable)
    }

    private fun GiftEntity.toDomain(): Gift {
        return Gift(
            id = this.id,
            name = this.name,
            cost = this.cost,
            icon = this.icon,
            isAvailable = this.isAvailable
        )
    }

    private fun Gift.toEntity(): GiftEntity {
        return GiftEntity(
            id = this.id,
            name = this.name,
            cost = this.cost,
            icon = this.icon,
            isAvailable = this.isAvailable
        )
    }
}
