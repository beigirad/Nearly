package ir.beigirad.data.store

import io.reactivex.Observable
import ir.beigirad.data.model.VenueEntity
import ir.beigirad.data.repository.DataStore
import ir.beigirad.data.repository.RemoteRepository
import javax.inject.Inject

class RemoteDataStore @Inject constructor(
    private val remoteRepository: RemoteRepository
) : DataStore {
    override fun getVenues(currentLatLng: Pair<Double, Double>): Observable<List<VenueEntity>> {
        return remoteRepository.getVenues(currentLatLng)
    }
}