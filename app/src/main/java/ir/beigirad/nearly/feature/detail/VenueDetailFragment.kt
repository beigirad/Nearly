package ir.beigirad.nearly.feature.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import ir.beigirad.nearly.IntentHelper
import ir.beigirad.nearly.R
import ir.beigirad.presentation.VenueDetailViewModel
import ir.beigirad.presentation.model.VenueDetailView
import ir.beigirad.presentation.state.Resource
import ir.beigirad.presentation.state.ResourceState
import ir.beigirad.zeroapplication.bases.BaseFragment
import ir.beigirad.zeroapplication.loadUrl
import ir.beigirad.zeroapplication.snack
import ir.beigirad.zeroapplication.widget.StateView
import ir.beigirad.zeroapplication.withArgs
import kotlinx.android.synthetic.main.content_detail.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 3/3/19.
 */
class VenueDetailFragment : BaseFragment() {
    override val childView: Int
        get() = R.layout.fragment_venue_detail

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: VenueDetailViewModel


    var venueId: String? = null

    override fun initVariables() {
        super.initVariables()
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VenueDetailViewModel::class.java)

        venueId = arguments?.getString(ARG_VENUE_ID)
    }

    companion object {
        const val ARG_VENUE_ID = "VENUE_ID"
        fun newInstance(venueId: String): VenueDetailFragment {
            return VenueDetailFragment().withArgs {
                putString(ARG_VENUE_ID, venueId)
            }
        }
    }

    override fun initUI() {
        super.initUI()
        viewModel.getDetail().observe(this, Observer {
            handleDetail(it)
        })
        venueId?.let { viewModel.fetchLoads(it) }
    }

    private fun handleDetail(venues: Resource<VenueDetailView>) {
        Timber.d("handleDetail ${venues.status}")
        when (venues.status) {
            ResourceState.LOADING ->
                if (venues.hasData)
                else {
                    detail_state.setState(StateView.State.LOADING)
                }
            ResourceState.SUCCESS -> {
                bindDetail(venues.data)

                if (venues.hasData)
                    detail_state.setState(StateView.State.SUCCESS)
                else
                    detail_state.setState(StateView.State.BLANK)
            }
            ResourceState.ERROR -> {
                if (venues.hasData) {
                    snack(detail_root, venues.message.toString(), Snackbar.LENGTH_INDEFINITE) {
                        venueId?.let { viewModel.fetchLoads(it) }
                    }
                } else {
                    detail_state.setErrorMessage(venues.message)
                    detail_state.setState(StateView.State.ERROR)
                }
            }
        }
    }

    private fun bindDetail(data: VenueDetailView?) {
        Timber.d("bindDetail $data")

        data?.run {
            detail_img.loadUrl(photoUrl)
            detail_prg.progress = rating100
            detail_rating.text = rating.toString()
            detail_rate.text = rating.toString()
            detail_title.text = primaryName
            detail_follower.text = follower.toString()
            detail_likes.text = likes.toString()

            detail_call.setOnClickListener {
//                IntentHelper.dialTo(context!!, this.phone)
            }
            detail_direction.setOnClickListener {
                IntentHelper.directTo(context!!, this.location.latLng)
            }
        }

    }


}