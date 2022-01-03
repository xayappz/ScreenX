package com.xayappz.screenx.db

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
class ImagesTable(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "user_id")
    var userId: String,

    @ColumnInfo(name = "img_name")
    var imgName: String,


)
