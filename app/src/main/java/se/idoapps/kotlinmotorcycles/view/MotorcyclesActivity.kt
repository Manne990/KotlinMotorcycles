package se.idoapps.kotlinmotorcycles.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.motorcycles_activity.*
import se.idoapps.kotlinmotorcycles.R
import se.idoapps.kotlinmotorcycles.common.*
import se.idoapps.kotlinmotorcycles.common.view.BaseActivity
import se.idoapps.kotlinmotorcycles.model.Motorcycle
import se.idoapps.kotlinmotorcycles.viewmodel.MotorcyclesViewModel
import javax.inject.Inject

class MotorcyclesActivity : BaseActivity(), View.OnClickListener {
    // Dagger
    @Inject
    lateinit var viewModel: MotorcyclesViewModel

    // Private Members
    private var _adapter: MotorcyclesAdapter? = null

    // Overrides
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init
        setContentView(R.layout.motorcycles_activity)
        setSupportActionBar(toolbar)

        // Init Dagger
        this.app?.appComponent?.inject(this)

        // Init the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load the motorcycles
        viewModel?.getMotorcycles()?.observe(this, Observer<List<Motorcycle>> {
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
