package ir.beigirad.domain.model


public sealed class GpsLocation {
    data class Success(val location: Pair<Double, Double>, val locationChanged: Boolean) : GpsLocation()
    data class Error(val message: String) : GpsLocation()
    data class Loading(val lastLocation: Location?) : GpsLocation()
}