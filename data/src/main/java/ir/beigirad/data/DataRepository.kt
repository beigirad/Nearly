package ir.beigirad.data

import io.reactivex.Observable
import io.reactivex.functions.Function3
import ir.beigirad.data.mapper.VenueDetailMapper
import ir.beigirad.data.mapper.VenueMapper
import ir.beigirad.data.repository.CacheRepository
import ir.beigirad.data.repository.PreferencesRepository
import ir.beigirad.data.repository.RemoteRepository
import ir.beigirad.data.store.DataStoreFactory
import ir.beigirad.domain.model.Venue
import ir.beigirad.domain.model.VenueDetail
import ir.beigirad.domain.repository.NearlyRepository
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val factory: DataStoreFactory,
    private val cache: CacheRepository,
    private val remote: RemoteRepository,
    private val preferences: PreferencesRepository,

    private val venueMapper: VenueMapper,
    private val detailMapper: VenueDetailMapper

) : NearlyRepository {
    override fun getVenues(locationLatLng: Pair<Double, Double>): Observable<List<Venue>> {
        return Observable.zip(
            cache.isCachedVenues().toObservable(),
            preferences.isExpiredCaches().toObservable(),
            remote.isOnline().toObservable(),
            Function3<Boolean, Boolean, Boolean, Triple<Boolean, Boolean, Boolean>>
            { isCached, isExpired, isOnline ->
                Triple(isCached, isExpired, isOnline)
            }
        ).flatMap { status ->
            factory.getDataStore(status.first, status.second, status.third)
                .getVenues(locationLatLng)
                .doAfterNext {
                    if (!factory.isFromCache(status.first, status.second, status.third))
                        cache.saveVenuesList(it)
                            .doOnComplete {
                                preferences.saveCacheTime(System.currentTimeMillis())
                            }
                            .subscribe()
                }
                .map { venuesList ->
                    venuesList.map {
                        venueMapper.mapFromEntity(it)
                    }
                }
        }
    }

    override fun getVenueDetail(venueId: String): Observable<VenueDetail> {
        return remote.getVenueDetail(venueId).map { detailMapper.mapFromEntity(it) }
    }

}