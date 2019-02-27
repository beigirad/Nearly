package ir.beigirad.domain.model

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
data class VenueDetail(
    val id: String,
    val primaryName: String?,
    val secondaryName: String?,
    val photoUrl: String?,
    val description: String?,
    val rating: Float,
    val ratingColor: String,
    val likes: Int,
    val follower: Int,

    val photos: List<Photo>?,
    val location: Location
) {
    data class Photo(
        val id: String,
        val url: String,
        val width: Int,
        val height: Int
    )
}