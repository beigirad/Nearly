package ir.beigirad.nearly.di.modules

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ir.beigirad.data.repository.RemoteRepository
import ir.beigirad.nearly.BuildConfig
import ir.beigirad.remote.RemoteRepositoryImpl
import ir.beigirad.remote.service.ApiService
import ir.beigirad.remote.service.ApiServiceFactory
import javax.inject.Singleton

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
@Module
abstract class RemoteModule {

    @Module
    companion object {
        @JvmStatic
        @Singleton
        @Provides
        fun provodeApiService(application: Application): ApiService {
            return ApiServiceFactory().makeRemoteService(BuildConfig.DEBUG, application.applicationContext)
        }
    }


    @Binds
    abstract fun bindRemoteRepository(remoteRepository: RemoteRepositoryImpl): RemoteRepository
}