package com.xayappz.screenx.utils

import androidx.lifecycle.ViewModel

import android.app.Application

import androidx.lifecycle.ViewModelProvider
import com.xayappz.screenx.viewmodels.DialogViewModel




class DialogFactory(
    private val application: Application?,
    private val myExtraParam: DeleteReview?
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DialogViewModel(application!!, myExtraParam!!) as T
}