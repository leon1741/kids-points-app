package com.leon.kidspoints.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leon.kidspoints.data.local.dao.*
import com.leon.kidspoints.data.local.entity.*

@Database(
    entities = [
        RuleEntity::class,
        PointRecordEntity::class,
        GiftEntity::class,
        RedemptionRecordEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ruleDao(): RuleDao
    abstract fun pointRecordDao(): PointRecordDao
    abstract fun giftDao(): GiftDao
    abstract fun redemptionRecordDao(): RedemptionRecordDao

    companion object {
        const val DATABASE_NAME = "kids_points_database"
    }
}
