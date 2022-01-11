package com.xayappz.screenx.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {
    private var itemDataList: MutableLiveData<ArrayList<String>> =
        MutableLiveData<ArrayList<String>>()
    private var itemDataListNEW: MutableLiveData<ArrayList<String>> =
        MutableLiveData<ArrayList<String>>()

    private var itemData: ArrayList<String> = ArrayList()
    private var itemDataNEW: ArrayList<String> = ArrayList()

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



    fun getDataFromSelectedNEW(): MutableLiveData<ArrayList<String>> {
        return itemDataListNEW

    }
    fun addDataFromSelectedNEW(data: String) {
        itemDataNEW.add(data)
        itemDataListNEW.postValue(itemDataNEW)
        getDataFromSelected()

    }

    fun removeDataFromSelectedNEW(data: String) {
        itemDataNEW.remove(data)
        itemDataListNEW.value?.remove(data)
        itemDataListNEW.postValue(itemDataNEW)
        getDataFromSelected()

    }

    fun removeAllNEW() {
        itemDataNEW.clear()
        itemDataListNEW.value?.clear()

    }

}