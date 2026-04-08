package com.leon.kidspoints.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 规则实体
 * @param id 规则 ID
 * @param name 规则名称（如：按时刷牙、完成作业）
 * @param points 积分值（正数=奖励，负数=扣分）
 * @param category 分类
 * @param icon Emoji 图标
 * @param isActive 是否启用
 * @param isFixed 是否为固定规则（固定规则不能标记为违规）
 */
@Entity(tableName = "rules")
data class RuleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val points: Int,
    val category: String,
    val icon: String,
    val isActive: Boolean = true,
    val isFixed: Boolean = false
)
