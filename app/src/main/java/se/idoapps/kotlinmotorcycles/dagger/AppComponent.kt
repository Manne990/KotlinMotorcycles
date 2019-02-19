package se.idoapps.kotlinmotorcycles.dagger

import dagger.Component
import se.idoapps.kotlinmotorcycles.view.EditMotorcycleActivity
import se.idoapps.kotlinmotorcycles.view.MotorcyclesActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    WebServiceModule::class,
    MotorcyclesViewModule::class,
    EditMotorcycleViewModule::class])

interface AppComponent {
    fun inject(target: MotorcyclesActivity)
    fun inject(target: EditMotorcycleActivity)
}