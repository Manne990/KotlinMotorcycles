package se.idoapps.kotlinmotorcycles.view

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.edit_motorcycle_activity.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.*
import se.idoapps.kotlinmotorcycles.R
import se.idoapps.kotlinmotorcycles.common.*
import se.idoapps.kotlinmotorcycles.common.view.BaseActivity
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.viewmodel.EditMotorcycleViewModelInterface
import javax.inject.Inject

class EditMotorcycleActivity : BaseActivity() {
    // Dagger
    @Inject
    lateinit var viewModel: EditMotorcycleViewModelInterface

    // Overrides
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init Dagger
        this.app.appComponent.inject(this)

        // Init Activity
        setContentView(R.layout.edit_motorcycle_activity)
        setSupportActionBar(toolbar)
        enableBackButton(true)

        // Observe Motorcycles Collection
        viewModel.data.observe(this, Observer {
            brandEditText.setText(it.brand)
            modelEditText.setText(it.model)
            yearEditText.setText(it.year.toString())
        })

        // Load the motorcycles
        val payload = intent.getSerializableExtra(PAYLOAD) as Motorcycle?
        GlobalScope.launch { viewModel.initWithPayload(payload) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_motorcycle_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menuDone) {
            viewModel.motorcycle.brand = brandEditText.text.toString()
            viewModel.motorcycle.model = modelEditText.text.toString()
            viewModel.motorcycle.year = yearEditText.text.toString().toIntOrNull() ?: 0

            GlobalScope.launch {
                val result = GlobalScope.async { viewModel.saveMotorcycle() }

                result.await()

                withContext(Dispatchers.Main) {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // Companion Objects
    companion object {
        const val PAYLOAD = "EditMotorcycleActivity_Payload"
    }
}