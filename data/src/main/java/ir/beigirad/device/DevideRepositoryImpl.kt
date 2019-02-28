package ir.beigirad.device

import android.Manifest
import androidx.annotation.RequiresPermission
import io.reactivex.Observable
import ir.beigirad.data.model.GpsLocationEntity
import ir.beigirad.data.repository.DeviceRepository
import ir.beigirad.device.location.LocationProvider
import ir.beigirad.device.mapper.LocationMapper
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class DevideRepositoryImpl @Inject constructor(
        private val locationProvider: LocationProvider,
        private val locationMapper: LocationMapper
) : DeviceRepository {

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun getLocation(): Observable<GpsLocationEntity> {
        Timber.d("getLocation ")
        return locationProvider.getLocationObservable().map {
            locationMapper.mapFromModel(it)
        }
    }


}