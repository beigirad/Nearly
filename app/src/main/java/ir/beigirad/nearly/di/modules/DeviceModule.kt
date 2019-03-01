package ir.beigirad.nearly.di.modules

import android.app.Application
import dagger.Binds
import dagger.Module
import ir.beigirad.data.repository.DeviceRepository
import ir.beigirad.device.DevideRepositoryImpl
import ir.beigirad.device.location.LocationProvider

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
@Module
abstract class DeviceModule {

    @Module
    companion object {
        fun provideLocationProvider(application: Application): LocationProvider {
            return LocationProvider(application.applicationContext)
        }
    }

    @Binds
    abstract fun bindDeviceRepository(deviceRepository: DevideRepositoryImpl): DeviceRepository

}