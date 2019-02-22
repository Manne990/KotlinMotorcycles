package se.idoapps.kotlinmotorcycles.application

import android.app.Application
import se.idoapps.kotlinmotorcycles.dagger.AppModule
import se.idoapps.kotlinmotorcycles.dagger.DaggerAppComponent
import se.idoapps.kotlinmotorcycles.dagger.AppComponent
import se.idoapps.kotlinmotorcycles.service.AnalyticsServiceInterface
import javax.inject.Inject

class MotorcyclesApp : Application() {
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var analytics: AnalyticsServiceInterface

    init {
        instance = this
    }

    companion object {
        private var instance: MotorcyclesApp? = null

        val application: MotorcyclesApp
            get() = instance!!
    }

    override fun onCreate() {
        super.onCreate()

        initDagger(this)
        initAnalytics()
    }

    private fun initDagger(app: MotorcyclesApp) {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(app))
            .build()

        appComponent.inject(this)
    }

    private fun initAnalytics() = analytics.init()
}