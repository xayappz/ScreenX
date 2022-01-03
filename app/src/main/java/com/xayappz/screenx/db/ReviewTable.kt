package com.xayappz.screenx.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class ReviewTable(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "userID")
    var userId: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "rating")
    var rating: Float,
    @ColumnInfo(name = "description")
    var comment: String,
    @ColumnInfo(name = "image")
    var image: String,
)