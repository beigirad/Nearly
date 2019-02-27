package ir.beigirad.data.model

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
data class VenueEntity(
    val id: String,
    val primaryName: String?,
    val secondaryName: String?,
    val photoUrl: String?,
    val description: String?,
    val userCount: Int,
    val location: LocationEntity,
    val category: CategoryEntity?
) {
    data class CategoryEntity(
        val id: String,
        val iconUrl: String,
        val title: String
    )
}