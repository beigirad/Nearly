package ir.beigirad.data.store

import io.reactivex.Completable
import io.reactivex.Observable
import ir.beigirad.data.model.PaginationEntity
import ir.beigirad.data.model.VenueEntity
import ir.beigirad.data.repository.CacheRepository
import ir.beigirad.data.repository.DataStore
import javax.inject.Inject

class CacheDataStore @Inject constructor(
    private val cacheRepository: CacheRepository
) : DataStore {

    override fun getVenues(pagination: PaginationEntity): Observable<List<VenueEntity>> {
        return cacheRepository.getVenues()
    }
}