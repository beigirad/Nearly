package ir.beigirad.data.repository

import io.reactivex.Observable
import ir.beigirad.data.model.PaginationEntity
import ir.beigirad.data.model.VenueEntity

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
interface DataStore {

    fun getVenues(pagination: PaginationEntity): Observable<List<VenueEntity>>

}
