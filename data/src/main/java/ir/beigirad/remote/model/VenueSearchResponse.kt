package ir.beigirad.remote.model

import com.google.gson.annotations.SerializedName

data class VenueSearchResponse(

    @field:SerializedName("venues")
    val venues: List<VenueItem>? = null
) {

    data class VenueItem(

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("location")
        val location: Location,

        @field:SerializedName("id")
        val id: String,

        @field:SerializedName("categories")
        val categories: List<Category>? = null
    )
}