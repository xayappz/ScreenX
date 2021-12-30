package com.xayappz.screenx.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.xayappz.screenx.R
import com.xayappz.screenx.db.Database
import com.xayappz.screenx.db.ReviewTable
import com.xayappz.screenx.models.ReviewImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DialogViewModel(application: Application) : AndroidViewModel(application) {
    private var database: Database =
        Room.databaseBuilder(application, Database::class.java, "reviewDB")
            .fallbackToDestructiveMigration().build()
    private var reviewData: ArrayList<ReviewImage> = ArrayList()

    private var mutableLiveDataReviewImage: MutableLiveData<ArrayList<ReviewImage>> =
        MutableLiveData<ArrayList<ReviewImage>>()

    fun sendUserAnswer(name: String?, review: String?, rating: Float?) {

        GlobalScope.launch(Dispatchers.IO) {
            database.reviewDAO()
                .insertReview(ReviewTable(0, name.toString(), rating!!, review.toString(), ""))
            getReview()
        }


    }


    suspend fun getReview(): MutableLiveData<ArrayList<ReviewImage>> {
        mutableLiveDataReviewImage.value?.clear()
        val profileDao = database.reviewDAO().loadAllReviews()
        for (data in profileDao) {
            reviewData.add(
                ReviewImage(
                    data.name,
                    data.comment,
                    R.drawable.model,
                    data.rating,
                    "30 Dec"
                )
            )
            mutableLiveDataReviewImage.postValue(reviewData)
        }

        return mutableLiveDataReviewImage

    }
//
//    suspend fun getReviewforEdit(): ReviewImage {
//        val profileDao = database.reviewDAO().loadAllReviews()
//
//        for (data in profileDao) {
//            reviewsforEdit.name=data.name
//            reviewsforEdit.description=data.comment
//            reviewsforEdit.description=data.image
//
//
//        }
//
//        return mutableLiveDataReviewImage
//
//    }
}