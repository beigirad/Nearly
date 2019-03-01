package ir.beigirad.remote.model

import com.google.gson.annotations.SerializedName

data class Location(

        @field:SerializedName("lat")
        val lat: Double,

        @field:SerializedName("lng")
        val lng: Double,

        @field:SerializedName("distance")
        val distance: Int,

        @field:SerializedName("formattedAddress")
        val formattedAddress: List<String>? = null,

        @field:SerializedName("address")
        val address: String? = null,

        @field:SerializedName("state")
        val state: String? = null

)