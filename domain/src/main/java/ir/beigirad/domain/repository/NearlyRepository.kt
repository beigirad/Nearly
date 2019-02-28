package ir.beigirad.domain.repository

import io.reactivex.Observable
import ir.beigirad.domain.model.GpsLocation
import ir.beigirad.domain.model.Venue
import ir.beigirad.domain.model.VenueDetail

interface NearlyRepository {

    fun getVenues(locationLatLng: Pair<Double, Double>): Observable<List<Venue>>

    fun getVenueDetail(venueId: String): Observable<VenueDetail>

    fun getMyLocation(): Observable<GpsLocation>

}