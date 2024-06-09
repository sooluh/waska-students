package id.my.suluh.waska.db.api

import id.my.suluh.waska.db.api.response.ResponseDetail
import id.my.suluh.waska.db.api.response.ResponseLatest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("students?limit=50")
    fun searchStudents(
        @Query("q") q: String?,
        @Query("page") page: Int?
    ): Call<ResponseLatest>

    @GET("students/{number}")
    fun getStudent(
        @Path("number") number: Int
    ): Call<ResponseDetail>

}
