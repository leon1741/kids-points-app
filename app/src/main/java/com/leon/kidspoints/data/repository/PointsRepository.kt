package com.leon.kidspoints.data.repository

import com.leon.kidspoints.data.local.dao.PointRecordDao
import com.leon.kidspoints.data.local.dao.RedemptionRecordDao
import com.leon.kidspoints.data.local.dao.RuleDao
import com.leon.kidspoints.data.local.entity.PointRecordEntity
import com.leon.kidspoints.data.local.entity.RedemptionRecordEntity
import com.leon.kidspoints.domain.model.PointRecord
import com.leon.kidspoints.domain.model.RecordType
import com.leon.kidspoints.domain.model.RedemptionRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PointsRepository(
    private val pointRecordDao: PointRecordDao,
    private val ruleDao: RuleDao,
    private val redemptionRecordDao: RedemptionRecordDao
) {

    val allRecords: Flow<List<PointRecord>> = pointRecordDao.getAllRecords().map { entities ->
        entities.map { it.toDomain() }
    }

    fun getTodayRecords(startOfDay: Long): Flow<List<PointRecord>> {
        return pointRecordDao.getTodayRecords(startOfDay).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getTotalPoints(): Int {
        val earned = pointRecordDao.getTotalPoints() ?: 0
        val redeemed = redemptionRecordDao.getTotalRedeemedPoints() ?: 0
        return earned - redeemed
    }

    suspend fun getTodayPoints(startOfDay: Long): Int {
        return pointRecordDao.getTodayPoints(startOfDay) ?: 0
    }

    suspend fun addCompleteRecord(ruleId: Int, note: String = "") {
        val rule = ruleDao.getRuleById(ruleId)
        val points = rule?.points ?: 0
        val record = PointRecordEntity(
            ruleId = ruleId,
            points = points,
            date = System.currentTimeMillis(),
            note = note,
            type = RecordType.COMPLETE
        )
        pointRecordDao.insertRecord(record)
    }

    suspend fun addViolationRecord(ruleId: Int, note: String = "") {
        val rule = ruleDao.getRuleById(ruleId)
        val points = rule?.points ?: 0
        val record = PointRecordEntity(
            ruleId = ruleId,
            points = -points, // 违规扣减对应积分
            date = System.currentTimeMillis(),
            note = note,
            type = RecordType.VIOLATION
        )
        pointRecordDao.insertRecord(record)
    }

    suspend fun addRedemptionRecord(giftId: Int, giftName: String, cost: Int) {
        val record = RedemptionRecordEntity(
            giftId = giftId,
            giftName = giftName,
            cost = cost,
            date = System.currentTimeMillis()
        )
        redemptionRecordDao.insertRecord(record)
    }

    private fun PointRecordEntity.toDomain(): PointRecord {
        return PointRecord(
            id = this.id,
            ruleId = this.ruleId,
            points = this.points,
            date = this.date,
            note = this.note,
            type = this.type
        )
    }
}
