package ir.beigirad.data.mapper

import ir.beigirad.data.model.VenueEntity
import ir.beigirad.domain.model.Venue
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class CategoryMapper @Inject constructor() : EntityMapper<VenueEntity.CategoryEntity, Venue.Category> {
    override fun mapFromEntity(entity: VenueEntity.CategoryEntity): Venue.Category {
        return Venue.Category(
            id = entity.id,
            title = entity.title,
            iconUrl = entity.iconUrl
        )
    }

    override fun mapToEntity(domain: Venue.Category): VenueEntity.CategoryEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}