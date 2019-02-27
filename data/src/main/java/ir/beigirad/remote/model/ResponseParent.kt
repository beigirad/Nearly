package ir.beigirad.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseParent<T>(

    @field:SerializedName("meta")
    val meta: Meta? = null,

    @field:SerializedName("response")
    val data: T? = null
) {
    val isSuccessful: Boolean
        get() = meta?.code == 200

    data class Meta(

        @field:SerializedName("code")
        val code: Int? = null,

        @field:SerializedName("requestId")
        val requestId: String? = null,

        @field:SerializedName("errorType")
        val errorType: String? = null,

        @field:SerializedName("errorDetail")
        val errorDetail: String? = null
    )

}