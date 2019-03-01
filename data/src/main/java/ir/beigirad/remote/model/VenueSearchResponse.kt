package ir.beigirad.remote.model

import com.google.gson.annotations.SerializedName

data class VenueSearchResponse(

        @field:SerializedName("groups")
        val groups: List<GroupItem>? = null
) {

    data class GroupItem(

            @field:SerializedName("type")
            val type: String,

            @field:SerializedName("name")
            val name: String,

            @field:SerializedName("items")
            val items: List<GroupSubItem>
    ) {

        data class GroupSubItem(

                @field:SerializedName("venue")
                val venue: VenueItem,

                @field:SerializedName("tips")
                val tips: List<Tip>? = null
        ) {
            data class VenueItem(

                    @field:SerializedName("id")
                    val id: String,

                    @field:SerializedName("name")
                    val name: String,

                    @field:SerializedName("contact")
                    val contact: Contact,


                    @field:SerializedName("location")
                    val location: Location,

                    @field:SerializedName("categories")
                    val categories: List<Category>? = null,

                    @field:SerializedName("price")
                    val price: Price? = null,

                    @field:SerializedName("rating")
                    val rating: Float? = null,

                    @field:SerializedName("ratingColor")
                    val ratingColor: String? = null

            ) {
                data class Price(

                        @field:SerializedName("tier")
                        val tier: Int? = null,

                        @field:SerializedName("currency")
                        val currency: String? = null,

                        @field:SerializedName("message")
                        val message: String? = null
                )
            }

            data class Tip(
                    @field:SerializedName("id")
                    val id: String,

                    @field:SerializedName("text")
                    val text: String? = null,

                    @field:SerializedName("user")
                    val user: User? = null,

                    @field:SerializedName("agreeCount")
                    val agreeCount: Int,

                    @field:SerializedName("disagreeCount")
                    val disagreeCount: Int,

                    @field:SerializedName("photo")
                    val photo: Photo?,

                    @field:SerializedName("photourl")
                    val photourl: String?

            ) {
                data class UserPhoto(

                        @field:SerializedName("prefix")
                        val prefix: String? = null,

                        @field:SerializedName("suffix")
                        val suffix: String? = null
                )

                data class User(

                        @field:SerializedName("firstName")
                        val firstName: String? = null,

                        @field:SerializedName("lastName")
                        val lastName: String? = null,

                        @field:SerializedName("photo")
                        val photo: UserPhoto? = null,

                        @field:SerializedName("id")
                        val id: String? = null
                )
            }
        }
    }


}