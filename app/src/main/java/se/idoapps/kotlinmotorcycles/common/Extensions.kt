package se.idoapps.kotlinmotorcycles.common

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import se.idoapps.kotlinmotorcycles.application.MotorcyclesApp

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

val Activity.app: MotorcyclesApp
    get() = application as MotorcyclesApp