package com.xayappz.screenx.viewmodels

import android.app.Application
import android.graphics.*
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.xayappz.screenx.db.Database
import com.xayappz.screenx.db.ReviewTable
import com.xayappz.screenx.models.ReviewImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class DialogViewModel(
    application: Application,

    ) : ViewModel() {
    private var database: Database =
        Room.databaseBuilder(application, Database::class.java, "reviewDB")
            .fallbackToDestructiveMigration().build()
    private var reviewData: ArrayList<ReviewImage> = ArrayList()
    private var reviewDataUser: ArrayList<ReviewImage> = ArrayList()

    private var mutableLiveDataReviewByUser: MutableLiveData<List<ReviewImage>> =
        MutableLiveData<List<ReviewImage>>()

    private var mutableLiveDataReviewImage: MutableLiveData<ArrayList<ReviewImage>> =
        MutableLiveData<ArrayList<ReviewImage>>()

    fun saveUserAnswer(
        name: String?,
        review: String?,
        rating: Float?,
        count: String?
    ) {

        var counter = 0
        if (!count.isNullOrEmpty()) {
            counter = count.toInt()

        }

        viewModelScope.launch(Dispatchers.IO) {
            database.reviewDAO()
                .insertReview(
                    ReviewTable(
                        0,
                        name.toString(),
                        rating!!,
                        review.toString(),
                        counter
                    )
                )

            getReviewByLimit()
        }
    }

    suspend fun deleteUserImage(
        userId: String?,
        newValue: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            database.reviewDAO().deleteImageById(newValue!!, userId!!)

        }
    }

    suspend fun deleteReview(userId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            database.reviewDAO().deleteReview(userId!!)
            getReviewByLimit()
        }
    }


    fun updateUserAnswer(
        id: String,
        name: String?,
        review: String?,
        rating: Float?,
        count: String?
    ) {
        var counter = 0
        if (!count.isNullOrEmpty()) {
            counter = count.toInt()

        }

        viewModelScope.launch(Dispatchers.IO) {
            database.reviewDAO()
                .updateReview(
                    ReviewTable(
                        id.toInt(), name!!, rating!!.toFloat(), review!!, counter
                    )
                )
            getReviewByLimit()
        }


    }

    suspend fun getReviewByLimit(): MutableLiveData<ArrayList<ReviewImage>> {
        reviewData.clear()
        mutableLiveDataReviewImage.value?.clear()
        val profileDao = database.reviewDAO().loadAllReviewsBylimit()
        if (profileDao.size == 0) {
            mutableLiveDataReviewImage.postValue(reviewData)
            return mutableLiveDataReviewImage
        }
        for (data in profileDao) {
            reviewData.add(
                ReviewImage(
                    data.id,
                    data.name,
                    data.comment,
                    data.image,
                    data.rating,
                    "30 Dec"
                )
            )
            mutableLiveDataReviewImage.postValue(reviewData)
        }


        return mutableLiveDataReviewImage

    }

    suspend fun getReview(): MutableLiveData<ArrayList<ReviewImage>> {
        reviewData.clear()
        mutableLiveDataReviewImage.value?.clear()
        val profileDao = database.reviewDAO().loadAllReviews()
        if (profileDao.size == 0) {
            mutableLiveDataReviewImage.postValue(reviewData)
            return mutableLiveDataReviewImage
        }
        for (data in profileDao) {
            reviewData.add(
                ReviewImage(
                    data.id,
                    data.name,
                    data.comment,
                    data.image,
                    data.rating,
                    "30 Dec"
                )
            )
            mutableLiveDataReviewImage.postValue(reviewData)
        }


        return mutableLiveDataReviewImage

    }


    suspend fun isUserExists(name: String?): Boolean {
        var isExists = false
        val profileDao = database.reviewDAO().isUserExists(name!!);

        if (profileDao.isNotEmpty()) {
            isExists = true

        }
        Log.d("ISEEEE", isExists.toString())
        return isExists

    }


    suspend fun getReviewById(userId: String?): MutableLiveData<List<ReviewImage>> {
        val profileDao = database.reviewDAO().getReviewById(userId.toString());

        for (data in profileDao) {

            reviewDataUser.add(
                ReviewImage(
                    data.id,
                    data.name,
                    data.comment,
                    data.image,
                    data.rating,
                    "30 Dec"
                )
            )
            mutableLiveDataReviewByUser.postValue(reviewDataUser)

        }

        return mutableLiveDataReviewByUser

    }


    fun stringtoBitmap(imageBytes: ByteArray): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val yuvimage = YuvImage(imageBytes, ImageFormat.YUY2, 120, 30, null)
            val baos = ByteArrayOutputStream()
            yuvimage.compressToJpeg(Rect(0, 0, 20, 20), 100, baos)
            val jdata = baos.toByteArray()
            bitmap = BitmapFactory.decodeByteArray(jdata, 0, jdata.size)
        } catch (e: Exception) {
        }
        //imageListSave?.add(bitmap)

        return bitmap
    }
}