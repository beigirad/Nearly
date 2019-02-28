package ir.beigirad.data.model


public sealed class GpsLocationEntity {
    data class Success(val location: LocationEntity) : GpsLocationEntity()
    data class Error(val message: String) : GpsLocationEntity()
    data class Loading(val lastLocation: LocationEntity?) : GpsLocationEntity()
}