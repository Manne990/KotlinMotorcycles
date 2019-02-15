package se.idoapps.kotlinmotorcycles.common.view

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

open class BaseActivity: AppCompatActivity() {
    inline fun <reified T:ViewModel> getViewModel() : T {
        return ViewModelProviders
            .of(this)
            .get(T::class.java)
    }
}