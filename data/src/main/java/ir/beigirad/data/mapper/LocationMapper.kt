package ir.beigirad.data.mapper

import ir.beigirad.data.model.LocationEntity
import ir.beigirad.domain.model.Location
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class LocationMapper @Inject constructor() : EntityMapper<LocationEntity, Location> {
    override fun mapFromEntity(entity: LocationEntity): Location {
        return Location(
            latLng = entity.latLng,
            address = entity.address,
            distance = entity.distance
        )
    }

    override fun mapToEntity(domain: Location): LocationEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}