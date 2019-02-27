package ir.beigirad.data.store

import ir.beigirad.data.repository.DataStore
import javax.inject.Inject

class DataStoreFactory @Inject constructor(
    private val cacheDataStore: CacheDataStore,
    private val remoteDataStore: RemoteDataStore
) {
    fun getDataStore(isCached: Boolean, cacheExpired: Boolean, isOnline: Boolean): DataStore {
        return if (isFromCache(isCached, cacheExpired, isOnline))
            cacheDataStore
        else
            remoteDataStore
    }

    fun isFromCache(isCached: Boolean, cacheExpired: Boolean, isOnline: Boolean): Boolean {
        return (isCached && (!cacheExpired || !isOnline))
    }
}