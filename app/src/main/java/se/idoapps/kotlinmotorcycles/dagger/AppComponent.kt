package se.idoapps.kotlinmotorcycles.dagger

import dagger.Component
import se.idoapps.kotlinmotorcycles.view.MotorcyclesActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    WebServiceModule::class,
    MotorcyclesViewModule::class])

interface AppComponent {
    fun inject(target: MotorcyclesActivity)
}