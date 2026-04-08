package com.leon.kidspoints.data.local.dao

import androidx.room.*
import com.leon.kidspoints.data.local.entity.GiftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GiftDao {

    @Query("SELECT * FROM gifts ORDER BY cost, name")
    fun getAllGifts(): Flow<List<GiftEntity>>

    @Query("SELECT * FROM gifts WHERE isAvailable = 1 ORDER BY cost, name")
    fun getAvailableGifts(): Flow<List<GiftEntity>>

    @Query("SELECT * FROM gifts WHERE id = :id")
    suspend fun getGiftById(id: Int): GiftEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGift(gift: GiftEntity): Long

    @Update
    suspend fun updateGift(gift: GiftEntity)

    @Delete
    suspend fun deleteGift(gift: GiftEntity)

    @Query("UPDATE gifts SET isAvailable = :isAvailable WHERE id = :id")
    suspend fun toggleGiftAvailable(id: Int, isAvailable: Boolean)
}
