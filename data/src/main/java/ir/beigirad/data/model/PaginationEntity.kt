package ir.beigirad.data.model

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class PaginationEntity(
        val latLng: Pair<Double, Double>,
        val radius: Int,
        val limit: Int,
        val offset: Int
)