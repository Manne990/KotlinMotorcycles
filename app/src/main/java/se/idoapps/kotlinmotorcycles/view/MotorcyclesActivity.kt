package se.idoapps.kotlinmotorcycles.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.motorcycles_activity.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.*
import se.idoapps.kotlinmotorcycles.R
import se.idoapps.kotlinmotorcycles.common.*
import se.idoapps.kotlinmotorcycles.common.view.BaseActivity
import se.idoapps.kotlinmotorcycles.common.view.SwipeToDeleteHandler
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.service.AnalyticsServiceAbstractions
import se.idoapps.kotlinmotorcycles.service.AnalyticsServiceInterface
import se.idoapps.kotlinmotorcycles.viewmodel.MotorcyclesViewModelInterface
import javax.inject.Inject

class MotorcyclesActivity : BaseActivity(), View.OnClickListener {
    // Dagger
    @Inject
    lateinit var viewModel: MotorcyclesViewModelInterface

    @Inject
    lateinit var analytics: AnalyticsServiceInterface

    // Private Members
    private var _adapter: MotorcyclesAdapter? = null

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init Dagger
        this.app.appComponent.inject(this)

        // Init Activity
        setContentView(R.layout.motorcycles_activity)
        setSupportActionBar(toolbar)
        configureBackButton(backButtonEnabled = true, upButtonEnabled = false)

        // Controls
        recyclerView.layoutManager = LinearLayoutManager(this)

        _adapter = MotorcyclesAdapter(this)
        recyclerView.adapter = _adapter

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteHandler(this) {
            val item = _adapter?.getItem(it.adapterPosition)

            _adapter?.removeItem(it.adapterPosition)

            item?.let {
                deleteMotorcycle(it)
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)

        fab.setOnClickListener {
            startActivityForResult<EditMotorcycleActivity>(CALLBACK_REQUEST_CODE, true)
        }

        swipeContainer.setColorSchemeResources(R.color.colorAccent)
        swipeContainer.setOnRefreshListener {
            loadMotorcycles()
        }

        // Observe Motorcycles Collection
        viewModel.motorcycles.observe(this, Observer {
            swipeContainer.isRefreshing = false
            _adapter?.setData(it)
        })

        // Load the motorcycles
        loadMotorcycles()
    }

    override fun onResume() {
        super.onResume()

        analytics.trackEvent(AnalyticsServiceAbstractions.Events.LIST_MOTORCYCLES)
    }

    // Overrides
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
        launch {
            withContext(Dispatchers.Main) {
                swipeContainer.isRefreshing = true
            }

            viewModel.loadMotorcyclesAsync()

            withContext(Dispatchers.Main) {
                swipeContainer.isRefreshing = false
            }
        }
    }

    private fun deleteMotorcycle(motorcycle: Motorcycle) {
        launch { viewModel.deleteMotorcycleAsync(motorcycle) }
    }

    // Companion Objects
    companion object {
        const val CALLBACK_REQUEST_CODE: Int = 12345
    }
}