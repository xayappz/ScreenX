package com.xayappz.screenx.models

data class Review(val name:String, val date:String, val details:String, val image:Int,
                  val reviewImages: ArrayList<ImageReview>?
)
