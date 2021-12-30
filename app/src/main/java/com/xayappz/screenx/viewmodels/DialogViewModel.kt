package com.xayappz.screenx.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xayappz.screenx.models.ReviewImage

class DialogViewModel : ViewModel() {

    private val mutableLiveData: MutableLiveData<ReviewImage> = MutableLiveData<ReviewImage>()

    fun sendUserAnswer(name: String?, review: String?, rating: Float?) {

        mutableLiveData.postValue(
            ReviewImage(
                name.toString(),
                review.toString(),
                "",
                rating!!
            )
        )


    }

    fun getReview(): MutableLiveData<ReviewImage> {
        return mutableLiveData

    }
}