package id.my.suluh.waska.db.api.response

import com.google.gson.annotations.SerializedName

data class ResponseLatest(
    @field:SerializedName("data")
    val data: ArrayList<StudentList>
)
