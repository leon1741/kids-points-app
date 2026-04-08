package com.leon.kidspoints.domain.model

/**
 * 规则数据类
 */
data class Rule(
    val id: Int = 0,
    val name: String,
    val points: Int,
    val category: String,
    val icon: String,
    val isActive: Boolean = true,
    val isFixed: Boolean = false
)

/**
 * 积分记录
 */
data class PointRecord(
    val id: Int = 0,
    val ruleId: Int,
    val ruleName: String = "",
    val points: Int,
    val date: Long,
    val note: String = "",
    val type: RecordType
)

/**
 * 礼品
 */
data class Gift(
    val id: Int = 0,
    val name: String,
    val cost: Int,
    val icon: String,
    val isAvailable: Boolean = true
)

/**
 * 兑换记录
 */
data class RedemptionRecord(
    val id: Int = 0,
    val giftId: Int,
    val giftName: String,
    val cost: Int,
    val date: Long
)

/**
 * 记录类型
 */
enum class RecordType {
    COMPLETE,     // 完成奖励
    VIOLATION,    // 违规扣分
    REDEEM        // 礼品兑换
}

/**
 * 分类枚举
 */
enum class Category(val displayName: String) {
    DAILY_LIFE("生活习惯"),
    STUDY("学习任务"),
    MANNERS("礼貌行为"),
    EXERCISE("运动健康"),
    OTHER("其他")
}
