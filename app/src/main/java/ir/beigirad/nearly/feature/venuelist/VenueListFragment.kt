package ir.beigirad.nearly.feature.venuelist

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import ir.beigirad.nearly.R
import ir.beigirad.presentation.VenuesViewModel
import ir.beigirad.zeroapplication.bases.BaseFragment
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
}