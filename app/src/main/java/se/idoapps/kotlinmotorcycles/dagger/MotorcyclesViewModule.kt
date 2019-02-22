package se.idoapps.kotlinmotorcycles.dagger

import dagger.Module
import dagger.Provides
import se.idoapps.kotlinmotorcycles.service.AnalyticsServiceInterface
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import se.idoapps.kotlinmotorcycles.viewmodel.MotorcyclesViewModel
import se.idoapps.kotlinmotorcycles.viewmodel.MotorcyclesViewModelInterface
import javax.inject.Singleton

@Module
class MotorcyclesViewModule {
    @Provides
    @Singleton
    fun provideMotorcyclesViewModel(webservice: WebServiceInterface, analytics: AnalyticsServiceInterface): MotorcyclesViewModelInterface = MotorcyclesViewModel(webservice, analytics)
}