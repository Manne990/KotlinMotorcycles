package se.idoapps.kotlinmotorcycles.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.motorcycles_activity.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import se.idoapps.kotlinmotorcycles.R
import se.idoapps.kotlinmotorcycles.common.*
import se.idoapps.kotlinmotorcycles.common.view.BaseActivity
import se.idoapps.kotlinmotorcycles.viewmodel.MotorcyclesViewModelInterface
import javax.inject.Inject

class MotorcyclesActivity : BaseActivity(), View.OnClickListener {
    // Dagger
    @Inject
    lateinit var viewModel: MotorcyclesViewModelInterface

    // Private Members
    private var _adapter: MotorcyclesAdapter? = null

    // Overrides
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init
        setContentView(R.layout.motorcycles_activity)
        setSupportActionBar(toolbar)

        // Init Dagger
        this.app.appComponent.inject(this)

        // Controls
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            startActivityForResult<EditMotorcycleActivity>(CALLBACK_REQUEST_CODE, true)
        }

        // Observe Motorcycles Collection
        viewModel.motorcycles.observe(this, Observer {
            _adapter = MotorcyclesAdapter(it, this)
            recyclerView.adapter = _adapter
        })

        // Load the motorcycles
        loadMotorcycles()
    }

    override fun onClick(view: View?) {
        val holder = view?.tag as MotorcyclesAdapter.MotorcycleViewHolder
        val item = _adapter?.getItem(holder.adapterPosition) ?: return

        startActivityForResult<EditMotorcycleActivity>(CALLBACK_REQUEST_CODE, true, EditMotorcycleActivity.PAYLOAD to item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != CALLBACK_REQUEST_CODE) {
            return
        }

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        loadMotorcycles()
    }

    // Private Functions
    private fun loadMotorcycles() {
        GlobalScope.launch { viewModel.loadMotorcycles() }
    }

    // Companion Objects
    companion object {
        const val CALLBACK_REQUEST_CODE: Int = 12345
    }
}
