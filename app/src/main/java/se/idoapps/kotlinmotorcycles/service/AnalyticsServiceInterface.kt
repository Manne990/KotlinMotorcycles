package se.idoapps.kotlinmotorcycles.service

interface AnalyticsServiceInterface {
    fun init()
    fun trackEvent(event: AnalyticsServiceAbstractions.Events, params: Map<String, String>? = null)
}

object AnalyticsServiceAbstractions {
    enum class Events {
        LIST_MOTORCYCLES,
        EDIT_MOTORCYCLE,
        NEW_MOTORCYCLE,
        DELETE_MOTORCYCLE
    }
}