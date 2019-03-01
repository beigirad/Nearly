package ir.beigirad.nearly.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import ir.beigirad.nearly.NearlyApplication
import ir.beigirad.nearly.di.modules.*
import javax.inject.Singleton

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        CacheModule::class, RemoteModule::class, PreferencesModule::class, DeviceModule::class,
        DataModule::class,
        UiModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent

    }

    fun inject(myApplication: NearlyApplication)
}