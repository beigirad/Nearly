package ir.beigirad.nearly

import android.app.Application
import timber.log.Timber

/**
 * Created by Farhad Beigirad on 2/28/19.
 */
class NearlyApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())


    }
}
