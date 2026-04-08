package com.leon.kidspoints.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 礼品实体
 * @param id 礼品 ID
 * @param name 礼品名称
 * @param cost 所需积分
 * @param icon Emoji 图标
 * @param isAvailable 是否可兑换
 */
@Entity(tableName = "gifts")
data class GiftEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val cost: Int,
    val icon: String,
    val isAvailable: Boolean = true
)
