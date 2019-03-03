package ir.beigirad.data.store

import ir.beigirad.data.repository.DataStore
import javax.inject.Inject

class DataStoreFactory @Inject constructor(
    private val cacheDataStore: CacheDataStore,
    private val remoteDataStore: RemoteDataStore
) {
    /*  isCached    isExpire    isOnline   changedLocation       DataStore
            0           0           0            0               remote(error)
            0           0           1            0               remote
            0           1           0            0               remote(error) - unexpected
            0           1           1            0               remote
            1           0           0            0             * cache
            1           0           1            0             * cache
            1           1           0            0             * cache
            1           1           1            0               remote
            0           0           0            1               remote(error)
            0           0           1            1               remote
            0           1           0            1               remote(error) - unexpected
            0           1           1            1               remote
            1           0           0            1               remote
            1           0           1            1               remote
            1           1           0            1               remote
            1           1           1            1               remote
     */
    fun isFromCache(status: DataStoreStatus): Boolean {
        return (status.isCached && !status.changedLocation && (!status.cacheExpired || !status.isOnline))
    }

    fun getDataStore(status: DataStoreStatus): DataStore {
        return if (isFromCache(status))
            cacheDataStore
        else
            return remoteDataStore
    }

    data class DataStoreStatus(
        val isCached: Boolean,
        val cacheExpired: Boolean,
        val isOnline: Boolean,
        val changedLocation: Boolean
    )

}