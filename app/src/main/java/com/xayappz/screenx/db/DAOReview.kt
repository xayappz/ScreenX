package com.xayappz.screenx.db

import androidx.room.*

@Dao
interface DAOReview {
    @Insert
    suspend fun insertReview(reviewImage: ReviewTable)

    @Insert
    suspend fun insertReviewImage(imagesTable: ImagesTable)

    @Query("Select * from review ORDER BY Id LIMIT 5")
    suspend fun loadAllReviewsBylimit(): List<ReviewTable>


    @Query("Select * from review ")
    suspend fun loadAllReviews(): List<ReviewTable>


    @Query("Select * from image")
    suspend fun loadAllReviewsImages(): List<ImagesTable>

    @Query("Delete FROM image WHERE img_name =:imageName ")
    fun deleteImage(imageName: String)

    @Query("UPDATE review SET count=:newValue where id =:userId")
    fun deleteImageById(newValue: String,userId: String)

    @Query("Delete FROM review WHERE id =:userId ")
    fun deleteReview(userId: String)

    @Update
    fun updateReview(reviewTable: ReviewTable)


    @Query("UPDATE review SET name=:namee & description=:desc & rating=:ratingg & count=:count WHERE id=:userId ")
    fun updateReview(namee: String, desc: String, ratingg: Float, userId: Int, count: Int)

    @Delete
    fun updateImageReview(reviewImage: ReviewTable)

    @Query("SELECT * FROM review WHERE id =:userId ")
    suspend fun getReviewById(userId: String): List<ReviewTable>

    @Query("SELECT * FROM review WHERE id =:userId ")
    suspend fun getCount(userId: String): List<ReviewTable>


    @Query("SELECT * FROM image WHERE id =:userId ")
    suspend fun getImagesById(userId: String): List<ImagesTable>

    @Query("SELECT * FROM review WHERE name =:name ")
    suspend fun isUserExists(name: String):List<ReviewTable>

}
