package ir.beigirad.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
@Entity
class VenueModel(
    @PrimaryKey
    val id: String,
    val primaryName: String?,
    val secondaryName: String?,
    val photoUrl: String?,
    val description: String?,
        val vote: Int,
//    val latLng: Pair<Double, Double>,
    val lat: Double,
    val lng: Double,
    val address: String?,
    val distance: Int,
    val categoryId: String?,
    val catIcon: String?,
        val rating: Float,
        val ratingColor: String,
    val catTitle: String?
)