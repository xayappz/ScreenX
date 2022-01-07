package com.xayappz.screenx.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {
    private var itemDataList: MutableLiveData<ArrayList<String>> =
        MutableLiveData<ArrayList<String>>()
    private var itemData: ArrayList<String> = ArrayList()

    suspend fun getDataFromSelected(): MutableLiveData<ArrayList<String>> {
        Log.d("SSSSSSS",itemDataList.value?.size.toString()+"l")
        return itemDataList

    }

    suspend fun addDataFromSelected(data: String) {
        itemData.add(data)
        itemDataList.value = itemData
        getDataFromSelected()

    }

    suspend fun removeDataFromSelected(data: String) {
        itemData.remove(data)
        itemDataList.value?.remove(data)
        getDataFromSelected()

    }
    suspend fun removeAll() {
        itemData.clear()
        itemDataList.value?.clear()

    }


}