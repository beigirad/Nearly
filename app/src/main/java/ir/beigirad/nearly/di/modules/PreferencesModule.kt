package ir.beigirad.nearly.di.modules

import android.app.Application
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import ir.beigirad.data.repository.PreferencesRepository
import ir.beigirad.preferences.PreferencesFactory
import ir.beigirad.preferences.PreferencesRepositoryImpl
import javax.inject.Singleton

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
@Module
abstract class PreferencesModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun provideSharedPreferences(application: Application): SharedPreferences {
            return PreferencesFactory().getInstance(application.applicationContext)
        }
    }


    @Binds
    abstract fun bindPreferencesRepository(preferencesRepository: PreferencesRepositoryImpl): PreferencesRepository

}