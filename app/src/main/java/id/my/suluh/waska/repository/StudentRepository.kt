package id.my.suluh.waska.repository

import android.app.Application
import androidx.lifecycle.LiveData
import id.my.suluh.waska.db.entity.StudentEntity
import id.my.suluh.waska.db.room.StudentDao
import id.my.suluh.waska.db.room.StudentDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StudentRepository(application: Application) {
    private val studentDao: StudentDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = StudentDatabase.getDatabase(application)
        studentDao = db.studentDao()
    }

    fun getAllStudents(): LiveData<List<StudentEntity>> = studentDao.getAllStudents()

    fun getStudentByNumber(number: Int): LiveData<StudentEntity> =
        studentDao.getStudentByNumber(number)

    fun insert(student: StudentEntity) = executorService.execute { studentDao.insert(student) }

    fun update(student: StudentEntity) = executorService.execute { studentDao.update(student) }

    fun delete(student: StudentEntity) = executorService.execute { studentDao.delete(student) }
}
