package ir.beigirad.remote.model

import com.google.gson.annotations.SerializedName

data class Category(


        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("icon")
        val icon: Icon? = null,


        @field:SerializedName("primary")
        val isPrimary: Boolean? = null
) {
    data class Icon(

            @field:SerializedName("prefix")
            val prefix: String? = null,

            @field:SerializedName("suffix")
            val suffix: String? = null
    )
}