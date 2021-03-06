package se.idoapps.kotlinmotorcycles.common.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import se.idoapps.kotlinmotorcycles.R

class SwipeToDeleteHandler(context: Context, private val onDelete: (RecyclerView.ViewHolder) -> Unit) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    private val background = ColorDrawable(Color.RED)
    private val xMark = ContextCompat.getDrawable(context, R.drawable.ic_clear_24dp)?.apply {
        setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
    }
    private val xMarkMargin = context.resources.getDimension(R.dimen.ic_clear_margin).toInt()


    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onDelete(viewHolder)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (viewHolder.adapterPosition < 0) return

        val view = viewHolder.itemView // the view being swiped

        // draw the red background, based on the offset of the swipe (dX)
        background.apply {
            setBounds(view.right + dX.toInt(), view.top, view.right, view.bottom)
            draw(c)
        }

        // draw the symbol
        xMark?.apply {
            val xt = view.top + (view.bottom - view.top - xMark.intrinsicHeight) / 2
            setBounds(
                view.right - xMarkMargin - xMark.intrinsicWidth,
                xt,
                view.right - xMarkMargin,
                xt + xMark.intrinsicHeight
            )
            draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}