package com.leon.kidspoints.data.local.dao

import androidx.room.*
import com.leon.kidspoints.data.local.entity.RedemptionRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RedemptionRecordDao {

    @Query("SELECT * FROM redemption_records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<RedemptionRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: RedemptionRecordEntity): Long

    @Delete
    suspend fun deleteRecord(record: RedemptionRecordEntity)

    @Query("SELECT SUM(cost) FROM redemption_records")
    suspend fun getTotalRedeemedPoints(): Int?
}
