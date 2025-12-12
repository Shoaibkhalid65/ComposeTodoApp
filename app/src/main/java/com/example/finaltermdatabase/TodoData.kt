package com.example.finaltermdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date


@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val text: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Date? = null,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
)

class DateTypeConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDAte(value: Long?): Date? {
        return if (value != null)
            Date(value)
        else {
            null
        }
    }
}