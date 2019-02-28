package ir.beigirad.remote.mapper

import ir.beigirad.data.model.LocationEntity
import ir.beigirad.data.model.VenueEntity
import ir.beigirad.remote.model.VenueSearchResponse
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class VenueMapper @Inject constructor() : ModelMapper<VenueSearchResponse.VenueItem, VenueEntity> {
    override fun mapFromModel(model: VenueSearchResponse.VenueItem): VenueEntity {
        val category = model.categories?.find { it.primary == true }

        return VenueEntity(
            id = model.id,
            primaryName = model.name,
            category = if (category != null) {
                VenueEntity.CategoryEntity(
                    id = category.id ?: "",
                    title = category.name ?: "",
                    iconUrl = "${category.icon?.prefix}${category.icon?.suffix}"
                )
            } else
                null,
            location = LocationEntity(
                latLng = Pair(model.location.lat, model.location.lng),
                distance = model.location.distance,
                address = model.location.address
            ),
            userCount = 0,
            secondaryName = "",
            description = "",
            photoUrl = ""
        )
    }

}