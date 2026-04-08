package com.leon.kidspoints.data.repository

import com.leon.kidspoints.data.local.dao.RuleDao
import com.leon.kidspoints.data.local.entity.RuleEntity
import com.leon.kidspoints.domain.model.Category
import com.leon.kidspoints.domain.model.Rule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RulesRepository(private val ruleDao: RuleDao) {

    val allRules: Flow<List<Rule>> = ruleDao.getAllRules().map { entities ->
        entities.map { it.toDomain() }
    }

    val activeRules: Flow<List<Rule>> = ruleDao.getActiveRules().map { entities ->
        entities.map { it.toDomain() }
    }

    val allCategories: Flow<List<String>> = ruleDao.getAllCategories().map { entities ->
        entities.map { it.toDisplayName() }
    }

    suspend fun getRuleById(id: Int): Rule? {
        return ruleDao.getRuleById(id)?.toDomain()
    }

    suspend fun insertRule(rule: Rule): Long {
        return ruleDao.insertRule(rule.toEntity())
    }

    suspend fun updateRule(rule: Rule) {
        ruleDao.updateRule(rule.toEntity())
    }

    suspend fun deleteRule(rule: Rule) {
        ruleDao.deleteRule(rule.toEntity())
    }

    suspend fun toggleRuleActive(id: Int, isActive: Boolean) {
        ruleDao.toggleRuleActive(id, isActive)
    }

    private fun RuleEntity.toDomain(): Rule {
        return Rule(
            id = id,
            name = this.name,
            points = this.points,
            category = this.category.toDisplayName(),
            icon = this.icon,
            isActive = this.isActive,
            isFixed = this.isFixed
        )
    }

    private fun Rule.toEntity(): RuleEntity {
        return RuleEntity(
            id = this.id,
            name = this.name,
            points = this.points,
            category = this.category.toDbKey(),
            icon = this.icon,
            isActive = this.isActive,
            isFixed = this.isFixed
        )
    }

    private fun String.toDisplayName(): String {
        return try {
            Category.valueOf(this).displayName
        } catch (e: IllegalArgumentException) {
            Category.OTHER.displayName
        }
    }

    private fun String.toDbKey(): String {
        return try {
            Category.valueOf(this).name
        } catch (e: IllegalArgumentException) {
            Category.entries.first { it.displayName == this }?.name ?: Category.OTHER.name
        }
    }
}
