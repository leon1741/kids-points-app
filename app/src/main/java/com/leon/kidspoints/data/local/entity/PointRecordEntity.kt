package com.leon.kidspoints.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 积分记录实体
 * @param id 记录 ID
 * @param ruleId 关联的规则 ID（礼品兑换时为 0）
 * @param points 积分变化值
 * @param date 时间戳
 * @param note 备注说明
 * @param type 记录类型：COMPLETE=完成，VIOLATION=违规，REDEEM=兑换
 */
@Entity(tableName = "point_records")
data class PointRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ruleId: Int,
    val points: Int,
    val date: Long,
    val note: String = "",
    val type: RecordType
)

enum class RecordType {
    COMPLETE,     // 完成奖励
    VIOLATION,    // 违规扣分
    REDEEM        // 礼品兑换
}
