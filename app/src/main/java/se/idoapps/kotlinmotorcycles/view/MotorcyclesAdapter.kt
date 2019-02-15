package se.idoapps.kotlinmotorcycles.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import se.idoapps.kotlinmotorcycles.R
import se.idoapps.kotlinmotorcycles.model.Motorcycle

class MotorcyclesAdapter(var items: List<Motorcycle>, var clickListener: View.OnClickListener) : RecyclerView.Adapter<MotorcyclesAdapter.MotorcycleViewHolder>() {
    // Overrides
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotorcycleViewHolder {
        return MotorcycleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.motorcycle_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MotorcycleViewHolder, position: Int) {
        holder.setData(items[position])
    }

    // Public Functions
    fun getItem(adapterPosition: Int): Motorcycle {
        return items[adapterPosition]
    }

    // View Holder
    inner class MotorcycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Private Members
        private var _titleTextView: TextView

        // Constructors
        init {
            itemView.setOnClickListener(clickListener)
            itemView.tag = this

            _titleTextView = itemView.findViewById(R.id.titleTextView) as TextView
        }

        // Public Functions
        fun setData(item: Motorcycle) {
            _titleTextView.text = "${item.brand} ${item.model} (${item.year})"
        }
    }
}