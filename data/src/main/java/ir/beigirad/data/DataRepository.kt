package ir.beigirad.data

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import io.reactivex.Observable
import io.reactivex.functions.Function4
import ir.beigirad.data.mapper.GpsLocationMapper
import ir.beigirad.data.mapper.PaginationMapper
import ir.beigirad.data.mapper.VenueDetailMapper
import ir.beigirad.data.mapper.VenueMapper
import ir.beigirad.data.repository.CacheRepository
import ir.beigirad.data.repository.DeviceRepository
import ir.beigirad.data.repository.PreferencesRepository
import ir.beigirad.data.repository.RemoteRepository
import ir.beigirad.data.store.DataStoreFactory
import ir.beigirad.domain.model.GpsLocation
import ir.beigirad.domain.model.Venue
import ir.beigirad.domain.model.VenueDetail
import ir.beigirad.domain.model.VenuePagination
import ir.beigirad.domain.repository.NearlyRepository
import timber.log.Timber
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dataStoreFactory: DataStoreFactory,
    private val cache: CacheRepository,
    private val remote: RemoteRepository,
    private val preferences: PreferencesRepository,
    private val device: DeviceRepository,

    private val paginationMapper: PaginationMapper,
    private val gpsLocationMapper: GpsLocationMapper,
    private val venueMapper: VenueMapper,
    private val detailMapper: VenueDetailMapper

) : NearlyRepository {

    private fun calculateDistance(start: Pair<Double, Double>, end: Pair<Double, Double>): Int {
        val location1 = Location("").apply {
            latitude = start.first
            longitude = start.second
        }
        val location2 = Location("").apply {
            latitude = end.first
            longitude = end.second
        }
        return location1.distanceTo(location2).toInt()
    }

    override fun getVenues(venuePagination: VenuePagination): Observable<List<Venue>> {
        Timber.d("getVenues ")
        return Observable.zip(
            cache.isCachedVenues(venuePagination.offset).toObservable(),
            preferences.isExpiredCaches(Const.expirationTime).toObservable(),
            remote.isOnline().toObservable(),
            preferences.hasChangedLocation(venuePagination.latLng, Const.minDistance).toObservable(),
            Function4<Boolean, Boolean, Boolean, Boolean, DataStoreFactory.DataStoreStatus>
            { isCached, isExpired, isOnline, changedLocation ->
                DataStoreFactory.DataStoreStatus(isCached, isExpired, isOnline, changedLocation)
                    .apply {
                        Timber.d("getVenues $this")
                    }
            })

            .flatMap { status ->
                dataStoreFactory.getDataStore(status)
                    .getVenues(
                        paginationMapper.mapToEntity(venuePagination)
                    )
                    .doAfterNext {
                        if (!dataStoreFactory.isFromCache(status)) {

                            if (status.cacheExpired || status.changedLocation)
                                cache.invalidateVenues().subscribe()

                            cache.saveVenuesList(it)
                                .doOnComplete {
                                    preferences.saveCacheTime(System.currentTimeMillis()).subscribe()
                                    preferences.saveCurrentLocation(venuePagination.latLng).subscribe()
                                }
                                .subscribe()
                        }
                    }
                    .map { venuesList ->
                        venuesList.map {
                            venueMapper.mapFromEntity(it)
                        }
                    }
            }
    }

    override fun getVenueDetail(venueId: String): Observable<VenueDetail> {
        Timber.d("getVenueDetail ")
        return remote.getVenueDetail(venueId).map { detailMapper.mapFromEntity(it) }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun getMyLocation(): Observable<GpsLocation> {
        Timber.d("getMyLocation ")
        return device.getLocation().map {
            gpsLocationMapper.mapFromEntity(it)
        }
    }

}