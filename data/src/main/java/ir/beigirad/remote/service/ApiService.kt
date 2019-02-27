package ir.beigirad.remote.service

import io.reactivex.Observable
import ir.beigirad.remote.model.ResponseParent
import ir.beigirad.remote.model.VenueDetailResponse
import ir.beigirad.remote.model.VenueSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("venues/search")
    fun searchVenues(@Query("ll") locationLatLng: String): Observable<Response<ResponseParent<VenueSearchResponse>>>

    @GET("venues/VENUE_ID")
    fun getVenueDetail(@Path("VENUE_ID") venueId: String): Observable<Response<ResponseParent<VenueDetailResponse>>>

}