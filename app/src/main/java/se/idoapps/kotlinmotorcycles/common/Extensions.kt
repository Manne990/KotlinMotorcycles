package se.idoapps.kotlinmotorcycles.common

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.core.widget.doAfterTextChanged
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import se.idoapps.kotlinmotorcycles.application.MotorcyclesApp
import java.io.Serializable

// JSON
inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

// EditText
fun EditText.textAsString(): String {
    return this.text.toString()
}

fun EditText.textAsInt(): Int {
    return this.textAsString().toIntOrNull() ?: 0
}

fun EditText.setInt(value: Int, zeroIsBlank: Boolean = true) {
    if (zeroIsBlank && value == 0) {
        this.setText("")
        return
    }
    this.setText(value.toString())
}

fun EditText.validate(validator: (String) -> Boolean, message: String): Boolean {
    this.doAfterTextChanged {
        this.error = if (validator(it.toString())) null else message
    }
    this.error = if (validator(this.text.toString())) null else message

    return this.error == null
}

// String
fun String.toIntOrZero(): Int {
    return this.toIntOrNull() ?: 0
}

// Validation
fun String.isValidEmail(): Boolean = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

// Application
val Activity.app: MotorcyclesApp
    get() = application as MotorcyclesApp

// Activity and Intent
fun <ViewT : View> Activity.bindView(@IdRes idRes: Int): Lazy<ViewT> { // Usage: private val planningText by bindView<TextView>(R.id.planning_text)
    return lazyUnsychronized {
        findViewById<ViewT>(idRes)
    }
}

fun <T> lazyUnsychronized(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)

inline fun <reified T: Activity> Activity.startActivityForResult(requestCode: Int, transitions: Boolean = false, vararg params: Pair<String, Any?>) {
    if (transitions) {
        this.startActivityForResult(
            createIntent<T>(params),
            requestCode,
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    } else {
        this.startActivityForResult(
            createIntent<T>(params),
            requestCode)
    }
}

inline fun <reified T: Activity> Context.createIntent(params: Array<out Pair<String, Any?>>? = null): Intent {
    val intent = Intent(this, T::class.java)
    if (params != null && params.isNotEmpty()) fillIntentArguments(intent, params)
    return intent
}

fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
    params.forEach {
        val value = it.second
        when (value) {
            null -> intent.putExtra(it.first, null as Serializable?)
            is Int -> intent.putExtra(it.first, value)
            is Long -> intent.putExtra(it.first, value)
            is CharSequence -> intent.putExtra(it.first, value)
            is String -> intent.putExtra(it.first, value)
            is Float -> intent.putExtra(it.first, value)
            is Double -> intent.putExtra(it.first, value)
            is Char -> intent.putExtra(it.first, value)
            is Short -> intent.putExtra(it.first, value)
            is Boolean -> intent.putExtra(it.first, value)
            is Serializable -> intent.putExtra(it.first, value)
            is Bundle -> intent.putExtra(it.first, value)
            is Parcelable -> intent.putExtra(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> intent.putExtra(it.first, value)
            is LongArray -> intent.putExtra(it.first, value)
            is FloatArray -> intent.putExtra(it.first, value)
            is DoubleArray -> intent.putExtra(it.first, value)
            is CharArray -> intent.putExtra(it.first, value)
            is ShortArray -> intent.putExtra(it.first, value)
            is BooleanArray -> intent.putExtra(it.first, value)
            else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
        return@forEach
    }
}