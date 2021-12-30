package com.xayappz.screenx.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "image")
class ImagesTable(
    @PrimaryKey var id: Int = 0,
    @ColumnInfo(name = "imagename")
    var name: String,
    @ColumnInfo(name = "userId")
    var userId: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray? = null

)
