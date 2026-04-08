package com.leon.kidspoints.data.local.dao

import androidx.room.*
import com.leon.kidspoints.data.local.entity.RuleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RuleDao {

    @Query("SELECT * FROM rules ORDER BY category, name")
    fun getAllRules(): Flow<List<RuleEntity>>

    @Query("SELECT * FROM rules WHERE isActive = 1 ORDER BY category, name")
    fun getActiveRules(): Flow<List<RuleEntity>>

    @Query("SELECT * FROM rules WHERE id = :id")
    suspend fun getRuleById(id: Int): RuleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRule(rule: RuleEntity): Long

    @Update
    suspend fun updateRule(rule: RuleEntity)

    @Delete
    suspend fun deleteRule(rule: RuleEntity)

    @Query("UPDATE rules SET isActive = :isActive WHERE id = :id")
    suspend fun toggleRuleActive(id: Int, isActive: Boolean)

    @Query("SELECT DISTINCT category FROM rules ORDER BY category")
    fun getAllCategories(): Flow<List<String>>
}
