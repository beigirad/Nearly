package ir.beigirad.cache.mapper

import ir.beigirad.cache.model.VenueModel
import ir.beigirad.data.model.LocationEntity
import ir.beigirad.data.model.VenueEntity
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class VenueMapper @Inject constructor() : CacheMapper<VenueModel, VenueEntity> {
    override fun mapFromCache(cache: VenueModel): VenueEntity {
        return VenueEntity(
            id = cache.id,
            primaryName = cache.primaryName,
            secondaryName = cache.secondaryName,
            description = cache.description,
            photoUrl = cache.photoUrl,
                vote = cache.vote,
            category = if (cache.categoryId != null && cache.catIcon != null && cache.catTitle != null)
                VenueEntity.CategoryEntity(
                    id = cache.categoryId,
                    iconUrl = cache.catIcon,
                    title = cache.catTitle
                ) else null,
            ratingColor = cache.ratingColor,
            rating = cache.rating,
            location = LocationEntity(
                latLng = Pair(cache.lat, cache.lng),
                distance = cache.distance,
                address = cache.address
            )
        )
    }

    override fun mapToCache(entity: VenueEntity): VenueModel {
        return VenueModel(
            id = entity.id,
            primaryName = entity.primaryName,
            secondaryName = entity.secondaryName,
            description = entity.description,
            photoUrl = entity.photoUrl,
            vote = entity.vote,
            address = entity.location.address,
            distance = entity.location.distance,
//            latLng = entity.location.latLng,
            lat = entity.location.latLng.first,
            lng = entity.location.latLng.second,
            categoryId = entity.category?.id,
            catIcon = entity.category?.iconUrl,
            rating = entity.rating,
            ratingColor = entity.ratingColor,
            catTitle = entity.category?.title
        )
    }
}