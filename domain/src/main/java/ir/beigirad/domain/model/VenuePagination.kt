package ir.beigirad.domain.model

/**
 * Created by Farhad Beigirad on 3/1/19.
 */
class VenuePagination(
        val latLng: Pair<Double, Double>,
        val radius: Int,
        val limit: Int,
        val offset: Int
)