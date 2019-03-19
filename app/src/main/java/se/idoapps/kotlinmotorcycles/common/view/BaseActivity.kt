package se.idoapps.kotlinmotorcycles.common.view

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseActivity: AppCompatActivity(), CoroutineScope {
    // Private Members
    private var _backButtonEnabled: Boolean = false
    private lateinit var job: Job

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        job = Job()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    // Overrides
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    override fun onBackPressed() {
        if (_backButtonEnabled) {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // Public Functions
    fun configureBackButton(backButtonEnabled: Boolean, upButtonEnabled: Boolean) {
        _backButtonEnabled = backButtonEnabled
        var localUpButtonEnabled = upButtonEnabled

        if(!_backButtonEnabled) {
            localUpButtonEnabled = false
        }

        actionBar?.setDisplayHomeAsUpEnabled(localUpButtonEnabled)
        supportActionBar?.setDisplayHomeAsUpEnabled(localUpButtonEnabled)
    }
}