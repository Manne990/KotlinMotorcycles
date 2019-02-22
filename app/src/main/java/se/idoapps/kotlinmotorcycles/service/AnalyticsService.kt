package se.idoapps.kotlinmotorcycles.service

import com.google.firebase.analytics.FirebaseAnalytics
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import se.idoapps.kotlinmotorcycles.application.MotorcyclesApp

class AnalyticsService : AnalyticsServiceInterface {
    private var _firebase: FirebaseAnalytics? = null

    companion object {
        private const val APP_CENTER_KEY = "40d6aca1-80ea-48a3-bfe1-cacf5666296f"
    }

    override fun init() {
        println("AnalyticsService: Starting...")

        AppCenter.start(MotorcyclesApp.application, APP_CENTER_KEY, Analytics::class.java, Crashes::class.java)
        _firebase = FirebaseAnalytics.getInstance(MotorcyclesApp.application)
    }

    override fun trackEvent(eventName: String) {
        println("AnalyticsService: Track event '$eventName'")

        Analytics.trackEvent(eventName)
        _firebase!!.logEvent(eventName, null)
    }
}