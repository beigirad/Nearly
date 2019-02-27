package ir.beigirad.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ir.beigirad.data.model.VenueDetailEntity
import ir.beigirad.data.model.VenueEntity

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
interface RemoteRepository {

    fun isOnline(): Single<Boolean>

    fun getVenues(currentLatLng: Pair<Double, Double>): Observable<List<VenueEntity>>

    fun getVenueDetail(venueId: String): Observable<VenueDetailEntity>

}