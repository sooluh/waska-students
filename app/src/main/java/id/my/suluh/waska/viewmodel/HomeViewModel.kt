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

    private val _keywords = MutableLiveData<String?>(null)
    val keywords: LiveData<String?> = _keywords

    private val _currentPage = MutableLiveData<Int?>(null)
    val currentPage: LiveData<Int?> = _currentPage

    init {
        searchStudent(null)
    }

    fun searchStudent(query: String?, page: Int? = 1) {
        if (_keywords.value == query && _currentPage.value == page) {
            return
        }

        _keywords.value = query
        _currentPage.value = page
        _isLoading.value = true

        ApiConfig.getApiService().searchStudents(query, page).apply {
            enqueue(object : Callback<ResponseLatest> {
                override fun onResponse(
                    call: Call<ResponseLatest>,
                    response: Response<ResponseLatest>
                ) {
                    val body = response.body()

                    if (!response.isSuccessful || body?.code != "OK") {
                        _isError.value = true
                    } else {
                        _studentList.value = body.data
                        _isError.value = false
                    }

                    _isLoading.value = false
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
