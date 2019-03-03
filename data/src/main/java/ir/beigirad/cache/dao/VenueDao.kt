package ir.beigirad.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single
import ir.beigirad.cache.model.VenueModel

@Dao
abstract class VenueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertVenues(provinces: List<VenueModel>)

    @Query("SELECT * FROM VenueModel")
    abstract fun getVenues(): Observable<List<VenueModel>>

    @Query("DELETE FROM VenueModel")
    abstract fun clearLoaders()

    @Query("SELECT Count(*) > :offset FROM VenueModel")
    abstract fun isCachedLoaders(offset: Int): Single<Boolean>

}