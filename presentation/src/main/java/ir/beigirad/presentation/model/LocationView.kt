package ir.beigirad.presentation.model

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
data class LocationView(
    val latLng: Pair<Double, Double>,
    val address: String?,
    val distance: Int
)