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
