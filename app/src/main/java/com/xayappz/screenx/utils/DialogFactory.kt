package com.xayappz.screenx.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xayappz.screenx.viewmodels.DialogViewModel


class DialogFactory(
    private val application: Application?,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DialogViewModel(application!!) as T
}