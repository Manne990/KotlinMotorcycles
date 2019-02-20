package se.idoapps.kotlinmotorcycles.application

import android.app.Application
import se.idoapps.kotlinmotorcycles.dagger.AppComponent
import se.idoapps.kotlinmotorcycles.dagger.AppModule
import se.idoapps.kotlinmotorcycles.dagger.DaggerAppComponent

class MotorcyclesApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = initDagger(this)
    }

    private fun initDagger(app: MotorcyclesApp): AppComponent =
        DaggerAppComponent
            .builder()
            .appModule(AppModule(app))
            .build()
}