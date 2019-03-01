package ir.beigirad.nearly.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun bindContext(application: Application): Context {
        return application.applicationContext
    }
}