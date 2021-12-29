package com.xayappz.screenx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.xayappz.screenx.adapters.ReviewAdapter
import com.xayappz.screenx.adapters.ViewPageAdapter
import com.xayappz.screenx.models.Review
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val imageList = mutableListOf<Int>()
    private val reviewList = ArrayList<Review>()
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dummyBannerImages()
        ViewPagerId.adapter = ViewPageAdapter(imageList)
        ViewPagerId.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        indicatorId.setViewPager(ViewPagerId)
        reviewAdapter = ReviewAdapter(reviewList)
        val layoutManager = LinearLayoutManager(applicationContext)
        review_recyclerView.layoutManager = layoutManager
        review_recyclerView.adapter = reviewAdapter
        prepareReviews()
    }

    private fun addBannerImages(image: Int) {
        imageList.add(image)
    }

    private fun dummyBannerImages() {

        for(i in 1..3)
            addBannerImages(R.drawable.yo)

    }
    private fun prepareReviews() {

        for(i in 1..5)
        {
            reviewList.add(Review("Akshay","Dec 29",getString(R.string.loreum),R.drawable.model))

        }
        reviewAdapter.notifyDataSetChanged()
    }
}