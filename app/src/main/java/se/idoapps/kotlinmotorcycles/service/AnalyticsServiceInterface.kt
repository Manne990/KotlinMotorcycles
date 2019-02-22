package se.idoapps.kotlinmotorcycles.service

interface AnalyticsServiceInterface {
    fun init()
    fun trackEvent(eventName: String, params: Map<String, String>? = null)
}