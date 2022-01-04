package com.xayappz.screenx.viewmodels

import android.app.Application
import android.graphics.*
import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.xayappz.screenx.R
import com.xayappz.screenx.db.Database
import com.xayappz.screenx.db.ImagesTable
import com.xayappz.screenx.db.ReviewTable
import com.xayappz.screenx.models.ImageReview
import com.xayappz.screenx.models.Images
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


    private var reviewDataallImage: ArrayList<Images> = ArrayList()


    private var reviewImagesUser: ArrayList<ImageReview> = ArrayList()
    lateinit var imageBytes: ByteArray


    private var mutableLiveDataReviewByUser: MutableLiveData<List<ReviewImage>> =
        MutableLiveData<List<ReviewImage>>()


    private var mutableLiveDataReviewImageByUser: ArrayList<ImageReview> =
        ArrayList<ImageReview>()


    private var mutableLiveDataReviewImage: MutableLiveData<ArrayList<ReviewImage>> =
        MutableLiveData<ArrayList<ReviewImage>>()
    private var mutableLiveDataReviewImageAll: MutableLiveData<ArrayList<Images>> =
        MutableLiveData<ArrayList<Images>>()


    fun saveUserAnswer(
        name: String?,
        review: String?,
        rating: Float?,
        count: String?
    ) {

        var counter= 0
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

    fun saveUserReviewImage(
        name: String,
        review: ArrayList<Bitmap?>
    ) {

        Log.d("review", review.toString())

        viewModelScope.launch(Dispatchers.IO) {
            for (data in review) {
                try {
                    database.reviewDAO()
                        .insertReviewImage(ImagesTable(0, name, data.toString()!!))

                } catch (e: java.lang.Exception) {
                    Log.d("SDDSD", e.toString())
                }
            }

        }
    }

    suspend fun deleteUserReviewImage(
        imageName: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            database.reviewDAO().deleteImage(imageName!!)

        }
    }
    suspend fun deleteUserImage(
        userId: String?,
        newValue: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            database.reviewDAO().deleteImageById(newValue!!,userId!!)

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
        var counter= 0
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

    suspend fun getReviewImage(): MutableLiveData<ArrayList<Images>> {
        reviewDataallImage.clear()
        mutableLiveDataReviewImage.value?.clear()
        val profileDao = database.reviewDAO().loadAllReviewsImages()
        Log.d("profileDao", profileDao.size.toString() + "..");
        if (profileDao.size > 0) {
            for (data in profileDao) {
                reviewDataallImage.add(
                    Images(data.imgName)
                )
                mutableLiveDataReviewImageAll.postValue(reviewDataallImage)
            }
        }

        return mutableLiveDataReviewImageAll

    }

    suspend fun getImagesbyId(userId: String?): ArrayList<ImageReview> {
        Log.d("IDDaaaa", userId.toString())

        val profileDao = database.reviewDAO().getImagesById(userId.toString());

        for (data in profileDao) {
            Log.d("IDDSSS", data.id.toString())


            imageBytes = Base64.decode(data.imgName, 0)

            reviewImagesUser.add(
                ImageReview(
                    0, stringtoBitmap(imageBytes)
                )
            )
            mutableLiveDataReviewImageByUser.addAll(reviewImagesUser)

        }

        return mutableLiveDataReviewImageByUser

    }

    suspend fun getCount(id: String?): Int {
        val profileDao = database.reviewDAO().getCount(id!!);

        return profileDao.size

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
//            Log.d("IDD", data.id.toString())
//            Log.d("NAME", data.name.toString())
//            Log.d("COMMENT", data.comment.toString())
//            Log.d("RATING", data.rating.toString())
//

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

    fun BitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
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