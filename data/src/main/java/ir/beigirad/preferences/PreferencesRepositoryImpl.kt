package ir.beigirad.preferences

import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single
import ir.beigirad.data.repository.PreferencesRepository
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class PreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PreferencesRepository {
    override fun isExpiredCaches(): Single<Boolean> {
        Timber.d("isExpiredCaches ")
        return Single.fromCallable {
            val lastCacheTime = sharedPreferences.getLong(Const.LAST_CACHE_TIME, 0)
            val cacheTime = System.currentTimeMillis() - lastCacheTime

            TimeUnit.MINUTES.convert(cacheTime, TimeUnit.MILLISECONDS) > Const.CACHE_EXPIRE_LIMIT
        }
    }

    override fun saveCacheTime(timeMillisecond: Long): Completable {
        Timber.d("saveCacheTime ")
        return Completable.fromCallable {
            sharedPreferences.edit().putLong(Const.LAST_CACHE_TIME, timeMillisecond).apply()
        }
    }

    override fun getCurrentLocation(): Single<Pair<Double, Double>> {
        Timber.d("getCurrentLocation ")
        return Single.fromCallable {
            val lat = sharedPreferences.getString(Const.LAST_LOCATION_LAT, "0.0")?.toDoubleOrNull()?:0.0
            val lng = sharedPreferences.getString(Const.LAST_LOCATION_LNG, "0.0")?.toDoubleOrNull()?:0.0
            Pair(lat, lng)
        }
    }

    override fun saveCurrentLocation(currentLatLng: Pair<Double, Double>): Completable {
        Timber.d("saveCurrentLocation ")
        return Completable.fromCallable {
            sharedPreferences.edit()
                .putString(Const.LAST_LOCATION_LAT, currentLatLng.first.toString())
                .putFloat(Const.LAST_LOCATION_LNG, currentLatLng.second.toFloat())
                .apply()
        }
    }
}