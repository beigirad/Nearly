package ir.beigirad.nearly.feature.venuelist

import android.Manifest
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import ir.beigirad.nearly.R
import ir.beigirad.nearly.utils.runtimepermission.PermissionHelper
import ir.beigirad.nearly.utils.runtimepermission.PermissionStatus
import ir.beigirad.presentation.VenuesViewModel
import ir.beigirad.zeroapplication.bases.BaseFragment
import ir.beigirad.zeroapplication.snack
import kotlinx.android.synthetic.main.fragment_venue_list.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class VenueListFragment : BaseFragment() {
    override val childView: Int
        get() = R.layout.fragment_venue_list

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: VenuesViewModel

    override fun initVariables() {
        super.initVariables()
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VenuesViewModel::class.java)
    }

    companion object {
        fun newInstance(): VenueListFragment {
            return VenueListFragment()
        }
    }

    override fun initUI() {
        super.initUI()
        checkLocationPermission(false)
    }

    private fun checkLocationPermission(force: Boolean) {
        Timber.d("checkLocationPermission force:$force")
        PermissionHelper.newRequest(Manifest.permission.ACCESS_FINE_LOCATION, force)
            .apply {
                getLiveData().observe(this, Observer {
                    handleLocationPermission(it)
                })
            }.request(childFragmentManager)
    }

    private fun handleLocationPermission(it: PermissionStatus) {
        when (it) {
            is PermissionStatus.Granted -> {
                Timber.d("checkLocationPermission Granted")

            }
            is PermissionStatus.Denied -> {
                Timber.d("checkLocationPermission Denied!")
                snack(venuelist_root, "${it.permission} was denied!") {
                    checkLocationPermission(true)
                }
            }
            is PermissionStatus.Blocked -> {
                Timber.d("checkLocationPermission Blocked!")
                snack(venuelist_root, "${it.permission} was blocked!\ncheck phone Settings") {
                    checkLocationPermission(true)
                }
            }
        }
    }

}