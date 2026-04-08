package com.leon.kidspoints.data.local.dao

import androidx.room.*
import com.leon.kidspoints.data.local.entity.PointRecordEntity
import com.leon.kidspoints.data.local.entity.RecordType
import kotlinx.coroutines.flow.Flow

@Dao
interface PointRecordDao {

    @Query("SELECT * FROM point_records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<PointRecordEntity>>

    @Query("SELECT * FROM point_records WHERE date >= :startOfDay ORDER BY date DESC")
    fun getTodayRecords(startOfDay: Long): Flow<List<PointRecordEntity>>

    @Query("SELECT * FROM point_records WHERE ruleId = :ruleId ORDER BY date DESC")
    fun getRecordsByRule(ruleId: Int): Flow<List<PointRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: PointRecordEntity): Long

    @Delete
    suspend fun deleteRecord(record: PointRecordEntity)

    @Query("SELECT SUM(points) FROM point_records")
    suspend fun getTotalPoints(): Int?

    @Query("SELECT SUM(points) FROM point_records WHERE date >= :startOfDay")
    suspend fun getTodayPoints(startOfDay: Long): Int?

    @Query("SELECT * FROM point_records WHERE type = :type ORDER BY date DESC LIMIT :limit")
    fun getRecordsByType(type: RecordType, limit: Int = 10): Flow<List<PointRecordEntity>>
}
