package ir.beigirad.remote.model

import com.google.gson.annotations.SerializedName

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