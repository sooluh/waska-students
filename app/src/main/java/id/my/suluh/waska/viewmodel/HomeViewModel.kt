package id.my.suluh.waska.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.my.suluh.waska.db.api.ApiConfig
import id.my.suluh.waska.db.api.response.ResponseLatest
import id.my.suluh.waska.db.api.response.StudentList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _studentList = MutableLiveData<ArrayList<StudentList>?>(null)
    val studentList: LiveData<ArrayList<StudentList>?> = _studentList

    init {
        searchStudent(null)
    }

    fun searchStudent(query: String?) {
        _isLoading.value = true

        ApiConfig.getApiService().searchStudents(query).apply {
            enqueue(object : Callback<ResponseLatest> {
                override fun onResponse(
                    call: Call<ResponseLatest>,
                    response: Response<ResponseLatest>
                ) {
                    if (response.isSuccessful) _studentList.value = response.body()?.data
                    else Log.e(TAG, response.message())

                    _isLoading.value = false
                    _isError.value = false
                }

                override fun onFailure(call: Call<ResponseLatest>, t: Throwable) {
                    Log.e(TAG, t.message.toString())

                    _studentList.value = arrayListOf()
                    _isError.value = true
                    _isLoading.value = false
                }
            })
        }
    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }
}
