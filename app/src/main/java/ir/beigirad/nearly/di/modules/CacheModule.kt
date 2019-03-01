package ir.beigirad.nearly.di.modules

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ir.beigirad.cache.CacheRepositoryImpl
import ir.beigirad.cache.db.AppDatabase
import ir.beigirad.cache.db.DatabaseFactory
import ir.beigirad.data.repository.CacheRepository
import javax.inject.Singleton

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun provideDatabase(application: Application): AppDatabase {
            return DatabaseFactory().getInstance(application.applicationContext)
        }
    }

    @Binds
    abstract fun bindCacheRepository(cacheRepository: CacheRepositoryImpl): CacheRepository

}