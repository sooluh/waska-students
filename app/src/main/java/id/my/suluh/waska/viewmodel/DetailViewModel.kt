package id.my.suluh.waska.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.my.suluh.waska.db.api.ApiConfig
import id.my.suluh.waska.db.api.response.ResponseDetail
import id.my.suluh.waska.db.api.response.StudentDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _student = MutableLiveData<StudentDetail?>()
    val student: LiveData<StudentDetail?> = _student

    fun getDetail(number: Int) {
        _isLoading.value = true

        ApiConfig.getApiService().getStudent(number).apply {
            enqueue(object : Callback<ResponseDetail> {
                override fun onResponse(
                    call: Call<ResponseDetail>,
                    response: Response<ResponseDetail>
                ) {
                    if (response.isSuccessful) _student.value = response.body()?.data
                    else Log.e(TAG, response.message())

                    _isLoading.value = false
                    _isError.value = false
                }

                override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                    Log.e(TAG, t.message.toString())

                    _isLoading.value = false
                    _isError.value = true
                }
            })
        }
    }

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }
}
