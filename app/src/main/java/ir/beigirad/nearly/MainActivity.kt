package ir.beigirad.nearly

import androidx.fragment.app.Fragment
import ir.beigirad.nearly.feature.venuelist.VenueListFragment
import ir.beigirad.zeroapplication.bases.BaseActivity

class MainActivity : BaseActivity() {
    override val contentView: Int
        get() = R.layout.activity_main

    override fun initUI() {
        super.initUI()
        showFragment(VenueListFragment.newInstance())
    }


    private fun showFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName)
                .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()
        else
            super.onBackPressed()
    }
}
