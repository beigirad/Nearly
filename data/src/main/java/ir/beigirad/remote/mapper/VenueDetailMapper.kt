package ir.beigirad.remote.mapper

import ir.beigirad.data.model.LocationEntity
import ir.beigirad.data.model.VenueDetailEntity
import ir.beigirad.remote.model.VenueDetailResponse
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class VenueDetailMapper @Inject constructor() : ModelMapper<VenueDetailResponse.Venue, VenueDetailEntity> {
    override fun mapFromModel(model: VenueDetailResponse.Venue): VenueDetailEntity {
        return VenueDetailEntity(
            id = model.id,
            location = LocationEntity(
                latLng = Pair(model.location.lat, model.location.lng),
                address = model.location.address,
                distance = model.location.distance
            ),
            photoUrl = "${model.bestPhoto?.prefix}${model.bestPhoto?.width}x${model.bestPhoto?.height}${model.bestPhoto?.suffix}",
            description = "",
            secondaryName = "",
            follower = 0,
            primaryName = model.name,
            rating = model.rating ?: 0F,
            ratingColor = model.ratingColor ?: "",
            likes = model.likes?.count ?: 0,
//            photos = model.photos?.groups?.flatMap { it!!.items }?.map {
//                VenueDetailEntity.PhotoEntity(
//                    id = it.id,
//                    width = it.width,
//                    url = "${it.prefix}${it.width}x${it.height}${it.suffix}",
//                    height = it.height
//                )
//            },
            photos = null


        )
    }
}