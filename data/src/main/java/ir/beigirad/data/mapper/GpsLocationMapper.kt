package ir.beigirad.data.mapper

import ir.beigirad.data.model.GpsLocationEntity
import ir.beigirad.domain.model.GpsLocation
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class GpsLocationMapper @Inject constructor(
        private val locationMapper: LocationMapper
) : EntityMapper<GpsLocationEntity, GpsLocation> {
    override fun mapFromEntity(entity: GpsLocationEntity): GpsLocation {
        return when (entity) {
            is GpsLocationEntity.Success -> GpsLocation.Success(locationMapper.mapFromEntity(entity.location))
            is GpsLocationEntity.Error -> GpsLocation.Error(entity.message)
            is GpsLocationEntity.Loading -> GpsLocation.Loading(entity.lastLocation?.let { locationMapper.mapFromEntity(it) })
        }
    }

    override fun mapToEntity(domain: GpsLocation): GpsLocationEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}