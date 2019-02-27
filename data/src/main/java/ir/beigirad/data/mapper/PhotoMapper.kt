package ir.beigirad.data.mapper

import ir.beigirad.data.model.VenueDetailEntity
import ir.beigirad.domain.model.VenueDetail
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class PhotoMapper @Inject constructor() : EntityMapper<VenueDetailEntity.PhotoEntity, VenueDetail.Photo> {
    override fun mapFromEntity(entity: VenueDetailEntity.PhotoEntity): VenueDetail.Photo {
        return VenueDetail.Photo(
            id = entity.id,
            height = entity.height,
            url = entity.url,
            width = entity.width
        )
    }

    override fun mapToEntity(domain: VenueDetail.Photo): VenueDetailEntity.PhotoEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}