package com.xayappz.screenx.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {

    private var itemDataListNEW: MutableLiveData<HashSet<String>> =
        MutableLiveData<HashSet<String>>()

    private var itemDataNEW: HashSet<String> = HashSet()


    fun getDataFromSelectedNEW(): MutableLiveData<HashSet<String>> {
        Log.d("SIZEData", itemDataListNEW.value?.size.toString())
        return itemDataListNEW

    }

    fun addDataFromSelectedNEW(data: String) {
        itemDataNEW.add(data)
        Log.d("SDSAD", data.toString())
        itemDataListNEW.postValue(itemDataNEW)
        getDataFromSelectedNEW()

    }

    fun removeDataFromSelectedNEW(data: String) {
        Log.d("NEWREMOCE", data)
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

    fun getSizeData(): Int? {
        return itemDataListNEW.value?.size

    }


}