package ir.beigirad.data.repository

import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
interface PreferencesRepository {

    fun isExpiredCaches(expireTime: Int): Single<Boolean>

    fun saveCacheTime(timeMillisecond: Long): Completable

    fun saveCurrentLocation(currentLatLng: Pair<Double, Double>): Completable

    fun hasChangedLocation(currentLatLng: Pair<Double, Double>, minDistance: Float): Single<Boolean>

}