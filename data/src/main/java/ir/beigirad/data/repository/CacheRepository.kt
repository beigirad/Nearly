package ir.beigirad.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ir.beigirad.data.model.VenueEntity

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
interface CacheRepository {

    fun saveVenuesList(venues:List<VenueEntity>): Completable

    fun clearVenues(): Completable

    fun getVenues(currentLatLng: Pair<Double, Double>): Observable<List<VenueEntity>>

    fun isCachedVenues(): Single<Boolean>

}