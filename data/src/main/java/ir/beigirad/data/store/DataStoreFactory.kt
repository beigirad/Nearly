package ir.beigirad.data.store

import ir.beigirad.data.repository.DataStore
import javax.inject.Inject

class DataStoreFactory @Inject constructor(
    private val cacheDataStore: CacheDataStore,
    private val remoteDataStore: RemoteDataStore
) {
    /*  firstPage    isExpire   isCached          DataStore
            0           0           0               remote
            0           0           1               remote
            0           1           0               remote
            0           1           1               remote
            1           0           0               remote
            1           0           1               cache
            1           1           0               remote
            1           1           1               remote
    */
    fun isFromCache(conditions: DataStoreConditions): Boolean {
        return (conditions.firstPage && conditions.isCached && !conditions.cacheExpired)
    }

    fun getDataStore(conditions: DataStoreConditions): DataStore {
        return if (isFromCache(conditions))
            cacheDataStore
        else
            return remoteDataStore
    }

    data class DataStoreConditions(
        val isCached: Boolean,
        val cacheExpired: Boolean,
        val firstPage: Boolean
    )

}