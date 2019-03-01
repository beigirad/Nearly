package ir.beigirad.remote.mapper

import ir.beigirad.data.model.LocationEntity
import ir.beigirad.data.model.VenueEntity
import ir.beigirad.remote.model.VenueSearchResponse
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class VenueMapper @Inject constructor() : ModelMapper<VenueSearchResponse.GroupItem.GroupSubItem, VenueEntity> {
    override fun mapFromModel(model: VenueSearchResponse.GroupItem.GroupSubItem): VenueEntity {
        val category = model.venue.categories?.find { it.isPrimary == true }
        val tip = model.tips?.getOrNull(0)
        val rate = DataUtils.getRandomRate()
        return VenueEntity(
                id = model.venue.id,
                primaryName = model.venue.name,
                category = if (category != null) {
                    VenueEntity.CategoryEntity(
                            id = category.id ?: "",
                            title = category.name ?: "",
                            iconUrl = DataUtils.wrapIcon(category.icon?.prefix, category.icon?.suffix)
                    )
                } else
                    null,
                location = LocationEntity(
                        latLng = Pair(model.venue.location.lat, model.venue.location.lng),
                        distance = model.venue.location.distance,
                        address = model.venue.location.address
                ),
                vote = 0,
                secondaryName = "",
                description = tip?.text,
                photoUrl = tip?.photourl,
                //TODO remove mock data!
                ratingColor = "#" + (model.venue.ratingColor ?: rate.second),
                rating = model.venue.rating ?: rate.first
        )
    }

}