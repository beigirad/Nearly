package ir.beigirad.nearly.feature.venuelist

import android.Manifest
import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import ir.beigirad.nearly.R
import ir.beigirad.nearly.utils.runtimepermission.PermissionHelper
import ir.beigirad.nearly.utils.runtimepermission.PermissionStatus
import ir.beigirad.presentation.VenuesViewModel
import ir.beigirad.presentation.model.VenueView
import ir.beigirad.presentation.state.Resource
import ir.beigirad.presentation.state.ResourceState
import ir.beigirad.zeroapplication.bases.BaseFragment
import ir.beigirad.zeroapplication.invisiable
import ir.beigirad.zeroapplication.snack
import ir.beigirad.zeroapplication.visiable
import ir.beigirad.zeroapplication.widget.EndlessRecyclerViewScrollListener
import ir.beigirad.zeroapplication.widget.StateView
import kotlinx.android.synthetic.main.content_venue_list.*
import kotlinx.android.synthetic.main.fragment_venue_list.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class VenueListFragment : BaseFragment(), VenueAdapter.IVenueAdapterListener {
    override fun onSelectVenue(selectedVenue: VenueView) {
        interaction.onSelectVenue(selectedVenue)
    }

    override val childView: Int
        get() = R.layout.fragment_venue_list

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: VenuesViewModel

    lateinit var adapter: VenueAdapter

    lateinit var interaction: IVenueListInteraction

    override fun initVariables() {
        super.initVariables()
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VenuesViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        interaction = when {
            context is IVenueListInteraction ->
                context
            parentFragment is IVenueListInteraction ->
                parentFragment as IVenueListInteraction
            else ->
                throw IllegalArgumentException("did not implement IVenueListInteraction")
        }
    }

    companion object {
        fun newInstance(): VenueListFragment {
            return VenueListFragment()
        }
    }

    override fun initUI() {
        super.initUI()
        val layoutManager = LinearLayoutManager(context)
        venuelist_ry.layoutManager = layoutManager
        adapter = VenueAdapter(this)
        venuelist_ry.adapter = adapter
        venuelist_ry.itemAnimator = null
        venuelist_ry.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        venuelist_ry.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Timber.d("onLoadMore $page")
                checkLocationPermission()
            }
        })

        venuelist_state.setRetryListener {
            checkLocationPermission()
        }


        viewModel.getVenues().observe(this, Observer {
            handleVenues(it)
        })
        checkLocationPermission()
    }

    private fun checkLocationPermission(force: Boolean = false) {
        Timber.d("checkLocationPermission force: $force")
        val permissionHelper = PermissionHelper.newRequest(Manifest.permission.ACCESS_FINE_LOCATION, force)
        permissionHelper.getLiveData().observe(this, Observer {
                    handleLocationPermission(it)
                })

        permissionHelper.request(childFragmentManager)
    }

    private fun handleLocationPermission(it: PermissionStatus) {
        when (it) {
            is PermissionStatus.Granted -> {
                Timber.d("checkLocationPermission Granted")
                viewModel.fetchMyLocation()
            }
            is PermissionStatus.Denied -> {
                Timber.d("checkLocationPermission Denied!")
                snack(venuelist_root, "Location permission was denied!", Snackbar.LENGTH_INDEFINITE) {
                    checkLocationPermission(true)
                }
            }
            is PermissionStatus.BlockedMessage -> {
                Timber.d("handleLocationPermission ShowMessage")
                snack(
                    venuelist_root,
                    "To show near locations you should grant Location permission.",
                    Snackbar.LENGTH_INDEFINITE
                ) {
                    checkLocationPermission(true)
                }
            }
        }
    }

    private fun handleVenues(venues: Resource<List<VenueView>>) {
        Timber.d("handleVenues ${venues.status}")
        when (venues.status) {
            ResourceState.LOADING ->
                if (venues.hasData)
                    venuelist_progress.visiable()
                else {
                    venuelist_state.setState(StateView.State.LOADING)
                    venuelist_progress.invisiable()
                }
            ResourceState.SUCCESS -> {
                refreshVenuesList(venues.data)

                if (venues.hasData)
                    venuelist_state.setState(StateView.State.SUCCESS)
                else
                    venuelist_state.setState(StateView.State.BLANK)

                venuelist_progress.invisiable()
            }
            ResourceState.ERROR -> {
                if (venues.hasData) {
                    snack(venuelist_root, venues.message.toString(), Snackbar.LENGTH_INDEFINITE) {
                        viewModel.fetchMyLocation()
                    }
                } else {
                    venuelist_state.setErrorMessage(venues.message)
                    venuelist_state.setState(StateView.State.ERROR)
                }
                venuelist_progress.invisiable()
            }
        }
    }

    private fun refreshVenuesList(data: List<VenueView>?) {
        Timber.d("refreshVenuesList ${data?.size}")
        data?.let {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
    }


}