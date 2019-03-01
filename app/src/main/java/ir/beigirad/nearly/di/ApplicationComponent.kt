package ir.beigirad.nearly.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import ir.beigirad.nearly.NearlyApplication
import ir.beigirad.nearly.di.modules.CacheModule
import ir.beigirad.nearly.di.modules.DataModule
import ir.beigirad.nearly.di.modules.RemoteModule
import ir.beigirad.nearly.di.modules.UiModule

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        CacheModule::class,RemoteModule::class,
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