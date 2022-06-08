package com.polyactiveteam.polyactive.room

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = false) var id: Int,
    val image: Bitmap?,
    val header: String,
    val newsDescription: String,
    val date: String,
    val dateLong: Long,
    val likeCounter: Int,
    val groupId: Int
)
