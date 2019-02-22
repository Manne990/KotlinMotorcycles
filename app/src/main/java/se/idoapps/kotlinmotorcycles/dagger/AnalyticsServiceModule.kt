package se.idoapps.kotlinmotorcycles.dagger

import dagger.Module
import dagger.Provides
import se.idoapps.kotlinmotorcycles.service.AnalyticsService
import se.idoapps.kotlinmotorcycles.service.AnalyticsServiceInterface
import javax.inject.Singleton

@Module
class AnalyticsServiceModule {
    @Provides
    @Singleton
    fun provideAnalyticsService(): AnalyticsServiceInterface = AnalyticsService()
}