package id.my.suluh.waska.db.api.response

import com.google.gson.annotations.SerializedName

data class StudentList(
    @field:SerializedName("number")
    val number: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("study")
    val study: String,
)
