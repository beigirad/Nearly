package ir.beigirad.nearly.feature.venuelist

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ir.beigirad.nearly.R
import ir.beigirad.presentation.model.VenueView
import ir.beigirad.zeroapplication.bases.BaseVH
import ir.beigirad.zeroapplication.inflate
import ir.beigirad.zeroapplication.loadUrl
import kotlinx.android.synthetic.main.item_venue.view.*
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class VenueAdapter @Inject constructor(
        var listener: IVenueAdapterListener
) : ListAdapter<VenueView, BaseVH<VenueView>>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH<VenueView> {
        return VenueVH(parent.inflate(R.layout.item_venue))
    }

    override fun onBindViewHolder(holder: BaseVH<VenueView>, position: Int) {
        holder.bindTo(getItem(position))
    }

    private inner class VenueVH(view: View) : BaseVH<VenueView>(view) {
        private val titleTv = itemView.item_venue_title
        private val categoryTv = itemView.item_venue_category
        private val ratingTv = itemView.item_venue_rate
        private val distanceTv = itemView.item_venue_distance
        private val img = itemView.item_venue_img

        init {
            itemView.setOnClickListener {
                listener.onSelectVenue(getItem(adapterPosition))
            }
        }

        override fun bindTo(item: VenueView) {
            item.apply {
                titleTv.text = primaryName
                categoryTv.text = category?.title
                distanceTv.text = location

                ratingTv.text = rating.toString()

                Color.parseColor(ratingColor).apply {
                    ratingTv.background.setColorFilter(this, PorterDuff.Mode.SRC_ATOP)
                }

                img.loadUrl(category?.iconUrl)
            }
        }

    }

    companion object {
        val diffUtilCallback = object : DiffUtil.ItemCallback<VenueView>() {
            override fun areItemsTheSame(oldItem: VenueView, newItem: VenueView): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VenueView, newItem: VenueView): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface IVenueAdapterListener {
        fun onSelectVenue(selectedVenue: VenueView)
    }

}