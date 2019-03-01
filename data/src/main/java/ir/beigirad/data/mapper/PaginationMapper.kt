package ir.beigirad.data.mapper

import ir.beigirad.data.model.PaginationEntity
import ir.beigirad.domain.model.VenuePagination
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class PaginationMapper @Inject constructor() : EntityMapper<PaginationEntity, VenuePagination> {
    override fun mapFromEntity(entity: PaginationEntity): VenuePagination {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mapToEntity(domain: VenuePagination): PaginationEntity {
        return PaginationEntity(
                latLng = domain.latLng,
                offset = domain.offset,
                limit = domain.limit,
                radius = domain.radius
        )
    }
}