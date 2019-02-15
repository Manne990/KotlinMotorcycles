package se.idoapps.kotlinmotorcycles.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.motorcycles_activity.*
import se.idoapps.kotlinmotorcycles.R
import se.idoapps.kotlinmotorcycles.common.view.BaseActivity
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.viewmodel.MotorcyclesViewModel

class MotorcyclesActivity : BaseActivity(), View.OnClickListener {
    // Private Members
    private var _viewModel: MotorcyclesViewModel? = null
    private var _adapter: MotorcyclesAdapter? = null

    // Overrides
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init
        _viewModel = getViewModel()

        setContentView(R.layout.motorcycles_activity)
        setSupportActionBar(toolbar)

        // Init the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load the motorcycles
        _viewModel?.getMotorcycles()?.observe(this, Observer<List<Motorcycle>> {
            it?.let {
                _adapter = MotorcyclesAdapter(it, this)
                recyclerView.adapter = _adapter
            }
        })
    }

    override fun onClick(view: View?) {
        val holder = view?.tag as MotorcyclesAdapter.MotorcycleViewHolder
        val item = _adapter?.getItem(holder.adapterPosition)

        println("Clicked: $item")
    }
}
