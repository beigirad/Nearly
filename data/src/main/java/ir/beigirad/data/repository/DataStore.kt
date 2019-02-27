package ir.beigirad.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import ir.beigirad.data.model.VenueEntity

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
interface DataStore {

    fun getVenues(currentLatLng: Pair<Double, Double>): Observable<List<VenueEntity>>

}
