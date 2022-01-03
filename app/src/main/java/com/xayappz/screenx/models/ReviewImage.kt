package com.xayappz.screenx.models


data class ReviewImage(
    var id:Int,
    var userId:String,
    var name: String,
    val description: String,
    val image: Int,
    val rating: Float,
    val date: String
)
