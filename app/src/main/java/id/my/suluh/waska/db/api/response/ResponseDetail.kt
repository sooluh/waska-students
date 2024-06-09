package id.my.suluh.waska.db.api.response

import com.google.gson.annotations.SerializedName

data class ResponseDetail(
    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: StudentDetail
)
