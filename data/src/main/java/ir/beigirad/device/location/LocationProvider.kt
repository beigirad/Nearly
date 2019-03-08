package ir.beigirad.device.location

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.annotation.RequiresPermission
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ir.beigirad.data.Const
import timber.log.Timber

/**
 * Created by Farhad Beigirad on 2/28/19.
 */
class LocationProvider(context: Context) : LocationListener {
    private val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager


    override fun onLocationChanged(location: Location) {
        observable.onNext(LocationStatus.Success(location))
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Timber.d("onStatusChanged: $provider $status")
    }

    @RequiresPermission(ACCESS_FINE_LOCATION)
    override fun onProviderEnabled(provider: String?) {
        Timber.d("onProviderEnabled: $provider")
        observable.onNext(LocationStatus.Loading(getLastLocation()))
    }

    override fun onProviderDisabled(provider: String?) {
        Timber.d("onProviderDisabled: $provider")
        observable.onNext(LocationStatus.Error("Location Provider turned-off"))
    }

    @RequiresPermission(ACCESS_FINE_LOCATION)
    private fun getLastLocation(): Location? {
        var location: Location? = null
        locationManager.getProviders(true).forEach {
            val lastKnownLocation = locationManager.getLastKnownLocation(it)
            if (location == null || lastKnownLocation.accuracy < location?.accuracy ?: 1000F)
                location = lastKnownLocation
        }
        return location
    }

    private val observable = BehaviorSubject.create<LocationStatus>()

    @RequiresPermission(ACCESS_FINE_LOCATION)
    fun getLocationObservable(): Observable<LocationStatus> {
        if (observable.hasObservers())
            return observable

        return observable.doOnSubscribe {
            Timber.d("doOnDispose: add Location listener")
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                Const.gpsInterval,
                Const.gpsMinDistance,
                this
            )
        }.doOnDispose {
            Timber.d("doOnDispose: removed Location update")
            locationManager.removeUpdates(this)
        }
    }
}