package ir.beigirad.remote.model

import com.google.gson.annotations.SerializedName

data class VenueDetailResponse(

    @field:SerializedName("venue")
    val venue: Venue? = null
) {
    data class Venue(

        @field:SerializedName("rating")
        val rating: Float? = null,

        @field:SerializedName("photos")
        val photos: Photos? = null,

        @field:SerializedName("url")
        val url: String? = null,

        @field:SerializedName("bestPhoto")
        val bestPhoto: Photo? = null,

        @field:SerializedName("colors")
        val colors: Colors? = null,

        @field:SerializedName("ratingSignals")
        val ratingSignals: Int? = null,

        @field:SerializedName("contact")
        val contact: Contact? = null,

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("ratingColor")
        val ratingColor: String? = null,

        @field:SerializedName("location")
        val location: Location,

        @field:SerializedName("id")
        val id: String,

        @field:SerializedName("categories")
        val categories: List<Category?>? = null,

        @field:SerializedName("likes")
        val likes: Likes? = null
    ) {
        data class Photos(

            @field:SerializedName("summary")
            val summary: String? = null,

            @field:SerializedName("count")
            val count: Int? = null,

            @field:SerializedName("groups")
            val groups: List<GroupsItem?>? = null
        ) {
            data class GroupsItem(

                @field:SerializedName("name")
                val name: String? = null,

                @field:SerializedName("count")
                val count: Int? = null,

                @field:SerializedName("type")
                val type: String? = null,

                @field:SerializedName("items")
                val items: List<Photo>
            )
        }

        data class Photo(

            @field:SerializedName("prefix")
            val prefix: String,

            @field:SerializedName("width")
            val width: Int,

            @field:SerializedName("id")
            val id: String,

            @field:SerializedName("suffix")
            val suffix: String,

            @field:SerializedName("height")
            val height: Int
        )

        data class Colors(

            @field:SerializedName("highlightColor")
            val highlightColor: HighlightColor? = null,

            @field:SerializedName("highlightTextColor")
            val highlightTextColor: HighlightColor? = null
        ) {
            data class HighlightColor(

                @field:SerializedName("value")
                val value: Int? = null
            )
        }

        data class Contact(

            @field:SerializedName("twitter")
            val twitter: String? = null,

            @field:SerializedName("facebookUsername")
            val facebookUsername: String? = null,

            @field:SerializedName("facebook")
            val facebook: String? = null,

            @field:SerializedName("instagram")
            val instagram: String? = null,

            @field:SerializedName("facebookName")
            val facebookName: String? = null
        )

        data class Likes(

            @field:SerializedName("summary")
            val summary: String? = null,

            @field:SerializedName("count")
            val count: Int? = null
        )
    }
}