package ir.beigirad.data.mapper

import ir.beigirad.data.model.VenueEntity
import ir.beigirad.domain.model.Venue
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class VenueMapper @Inject constructor(
    private val categoryMapper: CategoryMapper,
    private val locationMapper: LocationMapper
) : EntityMapper<VenueEntity, Venue> {
    override fun mapFromEntity(entity: VenueEntity): Venue {
        return Venue(
            id = entity.id,
            description = entity.description,
            photoUrl = entity.photoUrl,
            primaryName = entity.primaryName,
            secondaryName = entity.secondaryName,
                vote = entity.vote,
                rating = entity.rating,
                ratingColor = entity.ratingColor,
            category = entity.category?.let { categoryMapper.mapFromEntity(it) },
            location = entity.location.let { locationMapper.mapFromEntity(it) }
        )
    }

    override fun mapToEntity(domain: Venue): VenueEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}