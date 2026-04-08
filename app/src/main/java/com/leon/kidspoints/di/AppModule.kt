package com.leon.kidspoints.di

import android.content.Context
import androidx.room.Room
import com.leon.kidspoints.data.local.AppDatabase
import com.leon.kidspoints.data.local.dao.*
import com.leon.kidspoints.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRuleDao(database: AppDatabase): RuleDao {
        return database.ruleDao()
    }

    @Provides
    @Singleton
    fun providePointRecordDao(database: AppDatabase): PointRecordDao {
        return database.pointRecordDao()
    }

    @Provides
    @Singleton
    fun provideGiftDao(database: AppDatabase): GiftDao {
        return database.giftDao()
    }

    @Provides
    @Singleton
    fun provideRedemptionRecordDao(database: AppDatabase): RedemptionRecordDao {
        return database.redemptionRecordDao()
    }

    @Provides
    @Singleton
    fun provideRulesRepository(ruleDao: RuleDao): RulesRepository {
        return RulesRepository(ruleDao)
    }

    @Provides
    @Singleton
    fun providePointsRepository(
        pointRecordDao: PointRecordDao,
        ruleDao: RuleDao,
        redemptionRecordDao: RedemptionRecordDao
    ): PointsRepository {
        return PointsRepository(pointRecordDao, ruleDao, redemptionRecordDao)
    }

    @Provides
    @Singleton
    fun provideGiftsRepository(giftDao: GiftDao): GiftsRepository {
        return GiftsRepository(giftDao)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context)
    }
}
