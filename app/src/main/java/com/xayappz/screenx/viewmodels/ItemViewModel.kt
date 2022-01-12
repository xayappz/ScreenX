package com.xayappz.screenx.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {
    private var itemDataList: MutableLiveData<ArrayList<String>> =
        MutableLiveData<ArrayList<String>>()
    private var itemDataListNEW: MutableLiveData<HashSet<String>> =
        MutableLiveData<HashSet<String>>()

    private var itemData: ArrayList<String> = ArrayList()
    private var itemDataNEW: HashSet<String> = HashSet()

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



    fun getDataFromSelectedNEW(): MutableLiveData<HashSet<String>> {

        return itemDataListNEW

    }
    fun addDataFromSelectedNEW(data: String) {
        itemDataNEW.add(data)
        Log.d("SDSAD",data.toString())
        itemDataListNEW.value=itemDataNEW
        getDataFromSelectedNEW()

    }

    fun removeDataFromSelectedNEW(data: String) {
        Log.d("NEWREMOCE",data)
        itemDataNEW.remove(data)
        itemDataListNEW.value?.remove(data)
        itemDataListNEW.postValue(itemDataNEW)
        getDataFromSelectedNEW()

    }

    fun removeAllNEW() {
        itemDataNEW.clear()
        itemDataListNEW.value?.clear()
        getDataFromSelectedNEW()

    }

}