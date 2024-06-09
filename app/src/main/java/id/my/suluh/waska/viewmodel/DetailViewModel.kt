package id.my.suluh.waska.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.my.suluh.waska.db.api.ApiConfig
import id.my.suluh.waska.db.api.response.ResponseDetail
import id.my.suluh.waska.db.api.response.StudentDetail
import id.my.suluh.waska.db.entity.StudentEntity
import id.my.suluh.waska.repository.StudentRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val studentRepo: StudentRepository = StudentRepository(application)

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _student = MutableLiveData<StudentDetail?>()
    val student: LiveData<StudentDetail?> = _student

    fun insert(student: StudentEntity) = studentRepo.insert(student)
    fun delete(student: StudentEntity) = studentRepo.delete(student)
    fun isBookmarked(number: Int) = studentRepo.getStudentByNumber(number)

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

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            DetailViewModel(application) as T
    }

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }
}
