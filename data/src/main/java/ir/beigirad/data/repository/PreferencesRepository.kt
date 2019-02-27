package ir.beigirad.data.repository

import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
interface PreferencesRepository {

    fun isExpiredCaches(): Single<Boolean>

    fun saveCacheTime(timeMillisecond: Long): Completable

    fun getCurrentLocation(): Pair<Double, Double>

    fun saveCurrentLocation(currentLatLng: Pair<Double, Double>)

}