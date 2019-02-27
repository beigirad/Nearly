package ir.beigirad.domain.model

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
data class Venue(
    val id: String,
    val primaryName: String?,
    val secondaryName: String?,
    val photoUrl: String?,
    val description: String?,
    val userCount: Int,
    val location: Location,
    val category: Category?
) {
    data class Category(
        val id: String,
        val iconUrl: String,
        val title: String
    )
}