package se.idoapps.kotlinmotorcycles.common.view

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    private var _backButtonEnabled: Boolean = false

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