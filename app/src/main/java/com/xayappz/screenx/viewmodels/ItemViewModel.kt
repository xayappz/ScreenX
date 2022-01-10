package com.xayappz.screenx.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {
    private var itemDataList: MutableLiveData<ArrayList<String>> =
        MutableLiveData<ArrayList<String>>()
    private var itemData: ArrayList<String> = ArrayList()

    fun getDataFromSelected(): MutableLiveData<ArrayList<String>> {
        return itemDataList

    }

    fun addDataFromSelected(data: String) {
        itemData.add(data)
        itemDataList.postValue(itemData)
        getDataFromSelected()

    }

    fun removeDataFromSelected(data: String) {
        itemData.remove(data)
        itemDataList.value?.remove(data)
        itemDataList.postValue(itemData)
        getDataFromSelected()

    }

    fun removeAll() {
        itemData.clear()
        itemDataList.value?.clear()

    }


}