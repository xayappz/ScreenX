package com.xayappz.screenx.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImagesTable::class, ReviewTable::class], version = 2)
abstract class Database : RoomDatabase() {
    abstract fun reviewDAO(): DAOReview
}