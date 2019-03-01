package ir.beigirad.domain.repository

import io.reactivex.Observable
import ir.beigirad.domain.model.GpsLocation
import ir.beigirad.domain.model.Venue
import ir.beigirad.domain.model.VenueDetail
import ir.beigirad.domain.model.VenuePagination

interface NearlyRepository {

    fun getVenues(venuePagination: VenuePagination): Observable<List<Venue>>

    fun getVenueDetail(venueId: String): Observable<VenueDetail>

    fun getMyLocation(): Observable<GpsLocation>

}