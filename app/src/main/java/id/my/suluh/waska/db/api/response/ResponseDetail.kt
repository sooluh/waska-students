package id.my.suluh.waska.db.api.response

import com.google.gson.annotations.SerializedName

data class ResponseDetail(
    @field:SerializedName("data")
    val data: StudentDetail
)
