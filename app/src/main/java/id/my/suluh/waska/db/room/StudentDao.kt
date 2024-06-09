package id.my.suluh.waska.db.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.my.suluh.waska.db.entity.StudentEntity

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(studentEntity: StudentEntity)

    @Update
    fun update(studentEntity: StudentEntity)

    @Delete
    fun delete(studentEntity: StudentEntity)

    @Query("SELECT * FROM students ORDER BY number ASC")
    fun getAllStudents(): LiveData<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE number = :number")
    fun getStudentByNumber(number: Int): LiveData<List<StudentEntity>>
}
