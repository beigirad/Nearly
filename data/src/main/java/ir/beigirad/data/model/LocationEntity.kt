package ir.beigirad.data.model

data class LocationEntity(
    val latLng: Pair<Double, Double>,
    val address: String?,
    val distance: Int
)