package ir.beigirad.zeroapplication.bases

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseVH<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindTo(item:T)
}