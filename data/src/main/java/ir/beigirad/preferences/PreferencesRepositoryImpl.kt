package ir.beigirad.preferences

import android.content.SharedPreferences
import android.location.Location
import io.reactivex.Completable
import io.reactivex.Single
import ir.beigirad.data.repository.PreferencesRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class PreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PreferencesRepository {
    override fun hasChangedLocation(currentLatLng: Pair<Double, Double>, minDistance: Float): Single<Boolean> {
        val lt = sharedPreferences.getString(Const.LAST_LOCATION_LAT,"")
        val ln = sharedPreferences.getString(Const.LAST_LOCATION_LNG,"")
        return Single.fromCallable {

            val lat = lt?.toDoubleOrNull() ?: 0.0
            val lng = ln?.toDoubleOrNull() ?: 0.0
            val lastLocation = Location("").apply {
                latitude = lat
                longitude = lng
            }
            val currentLocation = Location("").apply {
                latitude = currentLatLng.first
                longitude = currentLatLng.second
            }
            currentLocation.distanceTo(lastLocation) > minDistance
        }
    }

    override fun isExpiredCaches(expireTime: Int): Single<Boolean> {
        Timber.d("isExpiredCaches ")
        return Single.fromCallable {
            val lastCacheTime = sharedPreferences.getLong(Const.LAST_CACHE_TIME, 0)
            val cacheTime = System.currentTimeMillis() - lastCacheTime

            cacheTime > expireTime
        }
    }

    override fun saveCacheTime(timeMillisecond: Long): Completable {
        Timber.d("saveCacheTime ")
        return Completable.fromCallable {
            sharedPreferences.edit().putLong(Const.LAST_CACHE_TIME, timeMillisecond).apply()
        }
    }

    override fun saveCurrentLocation(currentLatLng: Pair<Double, Double>): Completable {
        Timber.d("saveCurrentLocation ")
        return Completable.fromCallable {
            sharedPreferences.edit()
                .putString(Const.LAST_LOCATION_LAT, currentLatLng.first.toString())
                .putString(Const.LAST_LOCATION_LNG, currentLatLng.second.toString())
                .apply()
        }
    }
}