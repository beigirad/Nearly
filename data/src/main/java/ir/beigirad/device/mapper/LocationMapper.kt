package ir.beigirad.device.mapper

import ir.beigirad.data.model.GpsLocationEntity
import ir.beigirad.data.model.LocationEntity
import ir.beigirad.device.location.LocationStatus
import javax.inject.Inject

class LocationMapper @Inject constructor() {
    fun mapFromModel(device: LocationStatus): GpsLocationEntity {
        return when (device) {
            is LocationStatus.Success -> GpsLocationEntity.Success(LocationEntity(Pair(device.location.latitude, device.location.longitude), null, 0))
            is LocationStatus.Error -> GpsLocationEntity.Error(device.message)
            is LocationStatus.Loading -> GpsLocationEntity.Loading(device.lastLocation?.let { LocationEntity(Pair(device.lastLocation.latitude, device.lastLocation.longitude), null, 0) })
        }

    }

}