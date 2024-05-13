package id.my.suluh.waska.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "students")
@Parcelize
data class StudentEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "number")
    var number: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "study")
    var study: String
) : Parcelable
