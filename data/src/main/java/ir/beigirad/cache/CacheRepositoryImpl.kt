package ir.beigirad.cache

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ir.beigirad.cache.db.AppDatabase
import ir.beigirad.cache.mapper.VenueMapper
import ir.beigirad.data.model.VenueEntity
import ir.beigirad.data.repository.CacheRepository
import javax.inject.Inject

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class CacheRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val venueMapper: VenueMapper

) : CacheRepository {
    override fun saveVenuesList(venues: List<VenueEntity>): Completable {
        return Completable.fromCallable {
            db.venueDao().insertVenues(venues.map { venueMapper.mapToCache(it) })
        }
    }

    override fun clearVenues(): Completable {
        return Completable.fromCallable {
            db.venueDao().clearLoaders()
        }
    }

    override fun getVenues(): Observable<List<VenueEntity>> {
        return db.venueDao().getVenues().map { venueList ->
            venueList.map { venueMapper.mapFromCache(it) }
        }
    }

    override fun isCachedVenues(): Single<Boolean> {
        return db.venueDao().isCachedLoaders()
    }

}