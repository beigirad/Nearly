package ir.beigirad.nearly.feature.venuelist

import android.Manifest
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
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

}