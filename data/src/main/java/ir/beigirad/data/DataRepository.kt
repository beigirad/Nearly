package ir.beigirad.data

import android.Manifest
import androidx.annotation.RequiresPermission
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import ir.beigirad.data.mapper.LocationMapper
import ir.beigirad.data.mapper.PaginationMapper
import ir.beigirad.data.mapper.VenueDetailMapper
import ir.beigirad.data.mapper.VenueMapper
import ir.beigirad.data.model.GpsLocationEntity
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
    private val locationMapper: LocationMapper,
    private val venueMapper: VenueMapper,
    private val detailMapper: VenueDetailMapper

) : NearlyRepository {

    override fun getVenues(venuePagination: VenuePagination): Observable<List<Venue>> {
        Timber.d("getVenues ")
        val isFirstPage = venuePagination.offset == 0
        return Observable.zip(
            cache.isCachedVenues(venuePagination.offset).toObservable(),
            preferences.isExpiredCaches(Const.expirationTime).toObservable(),
            BiFunction<Boolean, Boolean, DataStoreFactory.DataStoreConditions>
            { isCached, isExpired ->
                DataStoreFactory.DataStoreConditions(
                    isCached = isCached,
                    cacheExpired = isExpired,
                        firstPage = isFirstPage
                )
            })
            .flatMap { conditions ->
                dataStoreFactory.getDataStore(conditions)
                    .getVenues(paginationMapper.mapToEntity(venuePagination))
                    .doAfterNext {
                        if (!dataStoreFactory.isFromCache(conditions)) {
                            if (isFirstPage)
                                cache.invalidateVenues()
                                        .andThen(cache.saveVenuesList(it))
                                        .andThen(preferences.saveCacheTime(System.currentTimeMillis()))
                                        .subscribe()
                            else
                                cache.saveVenuesList(it)
                                        .andThen(preferences.saveCacheTime(System.currentTimeMillis()))
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
        return device.getLocation()
            .flatMap { gpsLocation ->

                //  TODO using integrated mapper
                when (gpsLocation) {
                    is GpsLocationEntity.Success -> {
                        preferences
                            .hasChangedLocation(gpsLocation.location.latLng, Const.minDistance)
                            .toObservable()
                            .doOnNext { hasChanged ->
                                if (hasChanged)
                                    preferences.saveCurrentLocation(gpsLocation.location.latLng).subscribe()
                            }
                            .map { hasChanged ->
                                GpsLocation.Success(gpsLocation.location.latLng, hasChanged)
                            }
                    }
                    is GpsLocationEntity.Error ->
                        Observable.just(
                            GpsLocation.Error(gpsLocation.message)
                        )
                    is GpsLocationEntity.Loading ->
                        Observable.just(
                            GpsLocation.Loading(gpsLocation.lastLocation?.let { locationMapper.mapFromEntity(it) })
                        )
                }
            }
    }

}