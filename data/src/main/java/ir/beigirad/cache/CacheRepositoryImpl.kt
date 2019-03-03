package ir.beigirad.cache

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ir.beigirad.cache.db.AppDatabase
import ir.beigirad.cache.mapper.VenueMapper
import ir.beigirad.data.model.VenueEntity
import ir.beigirad.data.repository.CacheRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class CacheRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val venueMapper: VenueMapper

) : CacheRepository {
    override fun saveVenuesList(venues: List<VenueEntity>): Completable {
        Timber.d("saveVenuesList ")
        return Completable.fromCallable {
            db.venueDao().insertVenues(venues.map { venueMapper.mapToCache(it) })
        }
    }

    override fun invalidateVenues(): Completable {
        Timber.d("invalidateVenues ")
        return Completable.fromCallable {
            db.venueDao().clearLoaders()
        }
    }

    override fun getVenues(): Observable<List<VenueEntity>> {
        Timber.d("getVenues ")
        return db.venueDao().getVenues().map { venueList ->
            venueList.map { venueMapper.mapFromCache(it) }
        }
    }

    override fun isCachedVenues(offset: Int): Single<Boolean> {
        Timber.d("isCachedVenues ")
        return db.venueDao().isCachedLoaders(offset)
    }

}