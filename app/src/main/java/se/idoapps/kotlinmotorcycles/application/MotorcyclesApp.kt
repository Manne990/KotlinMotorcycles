package se.idoapps.kotlinmotorcycles.application

import android.app.Application
import se.idoapps.kotlinmotorcycles.dagger.AppComponent
import se.idoapps.kotlinmotorcycles.dagger.AppModule
import se.idoapps.kotlinmotorcycles.dagger.DaggerAppComponent
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class MotorcyclesApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = initDagger(this)

        initAppCenter()
    }

    private fun initDagger(app: MotorcyclesApp): AppComponent =
        DaggerAppComponent
            .builder()
            .appModule(AppModule(app))
            .build()

    private fun initAppCenter() =
        AppCenter.start(this, "40d6aca1-80ea-48a3-bfe1-cacf5666296f", Analytics::class.java, Crashes::class.java
    )
}