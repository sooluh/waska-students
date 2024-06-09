package id.my.suluh.waska.db.api.response

import com.google.gson.annotations.SerializedName

data class Meta(
    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("per_page")
    val perPage: Int,

    @field:SerializedName("current_page")
    val currentPage: Int,

    @field:SerializedName("first_page")
    val firstPage: Int,

    @field:SerializedName("previous_page")
    val previousPage: Int,

    @field:SerializedName("next_page")
    val nextPage: Int,

    @field:SerializedName("last_page")
    val lastPage: Int
)
