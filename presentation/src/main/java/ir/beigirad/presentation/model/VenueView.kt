package ir.beigirad.presentation.model

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
data class VenueView(
    val id: String,
    val primaryName: String?,
    val secondaryName: String?,
    val photoUrl: String?,
    val description: String?,
    val userCount: Int,
        val location: String,
        val rating: Float,
        val ratingColor: String,
    val category: CategoryView?
) {
    data class CategoryView(
        val id: String,
        val iconUrl: String,
        val title: String
    )
}