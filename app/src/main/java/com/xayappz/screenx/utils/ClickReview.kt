package com.xayappz.screenx.utils

import com.xayappz.screenx.models.Items

interface ClickReview {
    fun onCellClickListener(data: String)
    fun onCellClickListener(data: String, userId: String)

}

interface DeleteReview {
    fun onCellClickDelete(data: String)

}

interface DeleteImage {
    fun onCellDeleteImage(data: String, userId: String)

}

interface PassToActivity {
    fun onCellDeleteImage(data: String, userId: String)

}

interface ItemLongClickListener {
    fun onLongItemClicked(items: Items): Boolean
}

interface SelectAllListener {
    fun onAllSelectClicked(allSelected: Boolean)
}

interface UnSelectAllListener {
    fun onAllUnSelectClicked(allSelected: Boolean)
}

interface SelectedSingleListener {
    fun onSingleSelected(data: String)
}

interface unSelectedSingleListener {
    fun onUnSingleSelected(data: String)
}

interface ViewControl {
    fun onViewHandle(data: Boolean)
}

interface ItemLongClickListenerNew {
    fun onLongItemClicked(longPressed: Boolean, pos: String, intpos: Int): Boolean
}

interface ItemSingleSelectedNew {
    fun onSingleClickNEW(data: String, pos: Int, state: Boolean)
}

interface ItemSingleunSELECTNew {
    fun onSingleunClickNEW(data: String, pos: Int)
}

interface unCHECKSELECTALL {
    fun onunCHECKSELECTALL()
}