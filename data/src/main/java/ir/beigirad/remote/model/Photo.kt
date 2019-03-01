package ir.beigirad.remote.model

import com.google.gson.annotations.SerializedName

data class Photo(

	@field:SerializedName("prefix")
	val prefix: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("suffix")
	val suffix: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)