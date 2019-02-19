package se.idoapps.kotlinmotorcycles.dagger

import dagger.Module
import dagger.Provides
import se.idoapps.kotlinmotorcycles.service.WebServiceInterface
import se.idoapps.kotlinmotorcycles.viewmodel.*
import javax.inject.Singleton

@Module
class EditMotorcycleViewModule {
    @Provides
    @Singleton
    fun provideEditMotorcycleViewModel(webservice: WebServiceInterface): EditMotorcycleViewModelInterface = EditMotorcycleViewModel(webservice)
}