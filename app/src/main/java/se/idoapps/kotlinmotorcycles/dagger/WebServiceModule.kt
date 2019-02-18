package se.idoapps.kotlinmotorcycles.dagger

import dagger.Module
import dagger.Provides
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import se.idoapps.kotlinmotorcycles.service.WebService
import javax.inject.Singleton

@Module
class WebServiceModule {
    @Provides
    @Singleton
    fun provideWebService(): WebServiceInterface = WebService()
}