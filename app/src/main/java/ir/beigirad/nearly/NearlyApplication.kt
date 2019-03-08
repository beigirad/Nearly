package ir.beigirad.nearly

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import ir.beigirad.nearly.di.DaggerApplicationComponent
import ir.beigirad.nearly.utils.logging.DebugTree
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/28/19.
 */
class NearlyApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        Timber.plant(DebugTree())

        Stetho.initializeWithDefaults(this)

    }
}
