package ir.beigirad.remote

import android.content.Context
import io.reactivex.Observable
import io.reactivex.Single
import ir.beigirad.data.model.VenueDetailEntity
import ir.beigirad.data.model.VenueEntity
import ir.beigirad.data.repository.RemoteRepository
import ir.beigirad.remote.mapper.VenueDetailMapper
import ir.beigirad.remote.mapper.VenueMapper
import ir.beigirad.remote.service.ApiService
import ir.beigirad.remote.utils.ConnectivityHelper
import ir.beigirad.remote.utils.HttpErrorHandler
import ir.beigirad.remote.utils.NetworkErrorHandler
import javax.inject.Inject


/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class RemoteRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val venueMapper: VenueMapper,
    private val venueDetailMapper: VenueDetailMapper,

    private val context: Context
) : RemoteRepository {
    override fun isOnline(): Single<Boolean> {
        return Single.fromCallable {
            ConnectivityHelper.isConnectedToNetwork(context)
        }
    }

    override fun getVenues(currentLatLng: Pair<Double, Double>): Observable<List<VenueEntity>> {
        return api.searchVenues("${currentLatLng.first},${currentLatLng.second}")
            .onErrorResumeNext(NetworkErrorHandler())
            .map(HttpErrorHandler())
            .map { it.venues?.map { item -> venueMapper.mapFromModel(item) } }
    }

    override fun getVenueDetail(venueId: String): Observable<VenueDetailEntity> {
        return api.getVenueDetail(venueId)
            .onErrorResumeNext(NetworkErrorHandler())
            .map(HttpErrorHandler())
            .map { it.venue?.let { it1 -> venueDetailMapper.mapFromModel(it1) } }
    }
}