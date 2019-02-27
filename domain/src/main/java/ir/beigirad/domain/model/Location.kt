package ir.beigirad.domain.model

data class Location(
    val latLng: Pair<Double, Double>,
    val address: String?,
    val distance: Int
)