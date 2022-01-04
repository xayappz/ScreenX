package com.xayappz.screenx.db

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "image" , indices = [Index(value = ["img_name"], unique = true)])
class ImagesTable(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    @ColumnInfo(name = "name")
    var userName: String,
    @ColumnInfo(name = "img_name")
    var imgName: String,


)
