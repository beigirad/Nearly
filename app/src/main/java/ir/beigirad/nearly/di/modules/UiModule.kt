package ir.beigirad.nearly.di.modules

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.beigirad.domain.executor.PostExecutionThread
import ir.beigirad.nearly.di.UiThread
import ir.beigirad.nearly.feature.detail.VenueDetailFragment
import ir.beigirad.nearly.feature.venuelist.VenueListFragment

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread


    @ContributesAndroidInjector
    abstract fun contributeVenueListFragment(): VenueListFragment

    @ContributesAndroidInjector
    abstract fun contributeVenueDetailFragment(): VenueDetailFragment
}