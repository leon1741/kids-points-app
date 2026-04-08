package com.leon.kidspoints.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 兑换记录实体
 * @param id 记录 ID
 * @param giftId 礼品 ID
 * @param giftName 礼品名称（冗余存储，避免礼品删除后无法查看历史）
 * @param cost 消耗积分
 * @param date 时间戳
 */
@Entity(tableName = "redemption_records")
data class RedemptionRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val giftId: Int,
    val giftName: String,
    val cost: Int,
    val date: Long
)
