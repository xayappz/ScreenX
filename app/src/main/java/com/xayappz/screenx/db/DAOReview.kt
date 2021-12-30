package com.xayappz.screenx.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.xayappz.screenx.models.ReviewImage

@Dao
interface DAOReview {
    @Insert
    suspend fun insertReview(reviewImage: ReviewTable)

    @Query("Select * from review")
    suspend fun loadAllReviews():List<ReviewTable>

    @Delete
     fun deleteImage(reviewImage: ReviewTable)

    @Update
     fun updateReview(reviewImage: ReviewTable)

    @Delete
     fun updateImageReview(reviewImage: ReviewTable)


}
