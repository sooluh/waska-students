package id.my.suluh.waska.db.api.response

import com.google.gson.annotations.SerializedName

data class StudentDetail(
    @field:SerializedName("number")
    val number: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("gender")
    val gender: Char,

    @field:SerializedName("admission_year")
    val admissionYear: Int,

    @field:SerializedName("first_semester")
    val firstSemester: Int,

    @field:SerializedName("is_graduated")
    val isGraduated: Boolean,

    @field:SerializedName("education_level")
    val educationLevel: String,

    @field:SerializedName("study")
    val study: String,

    @field:SerializedName("pddikti")
    val pddikti: String,
)
