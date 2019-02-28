package ir.beigirad.device.location

import android.location.Location

public sealed class LocationStatus {
    data class Success(val location: Location) : LocationStatus()
    data class Error(val message: String) : LocationStatus()
    data class Loading(val lastLocation: Location?) : LocationStatus()
}