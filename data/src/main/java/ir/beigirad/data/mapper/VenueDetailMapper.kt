package ir.beigirad.data.mapper

import ir.beigirad.data.model.VenueDetailEntity
import ir.beigirad.domain.model.VenueDetail
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class VenueDetailMapper @Inject constructor(
    private val locationMapper: LocationMapper,
    private val photoMapper: PhotoMapper
) : EntityMapper<VenueDetailEntity, VenueDetail> {
    override fun mapFromEntity(entity: VenueDetailEntity): VenueDetail {
        return VenueDetail(
            id = entity.id,
            location = locationMapper.mapFromEntity(entity.location),
            primaryName = entity.primaryName,
            secondaryName = entity.secondaryName,
            photoUrl = entity.photoUrl,
            description = entity.description,
            follower = entity.follower,
            likes = entity.likes,
            rating = entity.rating,
            ratingColor = entity.ratingColor,
            photos = entity.photos?.map { photoMapper.mapFromEntity(it) }
        )
    }

    override fun mapToEntity(domain: VenueDetail): VenueDetailEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}