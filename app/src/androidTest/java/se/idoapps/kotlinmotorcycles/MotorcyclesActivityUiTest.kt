package se.idoapps.kotlinmotorcycles

import android.app.Activity
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.edit_motorcycle_activity.*
import kotlinx.android.synthetic.main.motorcycles_activity.*
import se.idoapps.kotlinmotorcycles.view.MotorcyclesActivity
import se.idoapps.kotlinmotorcycles.view.MotorcyclesAdapter
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage


@RunWith(AndroidJUnit4::class)
class MotorcyclesActivityUiTest {

    companion object {
        @BeforeClass
        fun setup() {
            println("Setup...")
        }
    }

    @get:Rule
    val motorcyclesActivityRule: ActivityTestRule<MotorcyclesActivity> = ActivityTestRule(MotorcyclesActivity::class.java)

    @Test
    fun loadAndClickItemInList() {
        val rvCount = getRvCount()

        if (rvCount > 0) {
            // Perform click on the first item in the recyclerview
            onView(withId(motorcyclesActivityRule.activity.recyclerView.id))
                .perform(RecyclerViewActions.actionOnItemAtPosition<MotorcyclesAdapter.MotorcycleViewHolder>(0, click()))

            // Check the value in the brand EditText
            onView(withId(getActivity()?.brandEditText?.id!!))
                .check(ViewAssertions.matches(withText("Honda")))
        }
    }

    private fun getRvCount(): Int {
        val recyclerView = motorcyclesActivityRule.activity.recyclerView
        return recyclerView.adapter!!.itemCount
    }

    private fun getActivity(): Activity? {
        var currentActivity: Activity? = null

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.runOnMainSync {
            val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = resumedActivities.iterator().next() as Activity
            }
        }

        return currentActivity
    }
}