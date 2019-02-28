package ir.beigirad.domain.model


public sealed class GpsLocation {
    data class Success(val location: Location) : GpsLocation()
    data class Error(val message: String) : GpsLocation()
    data class Loading(val lastLocation: Location?) : GpsLocation()
}