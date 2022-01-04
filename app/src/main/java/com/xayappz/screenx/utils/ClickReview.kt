package com.xayappz.screenx.utils

import com.xayappz.screenx.db.ReviewTable
import com.xayappz.screenx.models.ImageReview

interface ClickReview {
    fun onCellClickListener(data: String)
    fun onCellClickListener(data: String,userId:String)

}
interface DeleteReview{
    fun onCellClickDelete(data: String)

}
interface DeleteImage{
    fun onCellDeleteImage(data: String,userId: String)

}
interface PassToActivity{
    fun onCellDeleteImage(data: String,userId: String)

}