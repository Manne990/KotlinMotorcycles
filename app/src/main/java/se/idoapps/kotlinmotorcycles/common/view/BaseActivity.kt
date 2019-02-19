package se.idoapps.kotlinmotorcycles.common.view

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    private var backButtonEnabled: Boolean = false

    override fun onBackPressed() {
        if (backButtonEnabled) {
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

    fun enableBackButton(enable: Boolean) {
        backButtonEnabled = enable

        actionBar?.setDisplayHomeAsUpEnabled(enable)
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
    }
}