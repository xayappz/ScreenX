package com.xayappz.screenx

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.xayappz.screenx.adapters.ReviewAdapter
import com.xayappz.screenx.adapters.ViewPageAdapter
import com.xayappz.screenx.db.Database
import com.xayappz.screenx.db.ReviewTable
import com.xayappz.screenx.models.ImageReview
import com.xayappz.screenx.models.Review
import com.xayappz.screenx.utils.ReviewDialog
import com.xayappz.screenx.viewmodels.DialogViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.review_item.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val imageList = mutableListOf<Int>()
    private val reviewList = ArrayList<Review>()
     var getreviewList : List<ReviewTable> = ArrayList()
    private lateinit var reviewAdapter: ReviewAdapter
    private val reviewImageList = ArrayList<ImageReview>()

    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database =
            Room.databaseBuilder(applicationContext, Database::class.java, "reviewDB")
                .fallbackToDestructiveMigration().build()
        dummyBannerImages()
        val mainViewModel = ViewModelProvider(this)[DialogViewModel::class.java]
        ViewPagerId.adapter = ViewPageAdapter(imageList)
        ViewPagerId.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        images_review_recyclerView
        indicatorId.setViewPager(ViewPagerId)
        reviewAdapter = ReviewAdapter(reviewList, this)


        val layoutManager = LinearLayoutManager(applicationContext)

        review_recyclerView.layoutManager = layoutManager



        review_recyclerView.adapter = reviewAdapter
        add_reviewBn.setOnClickListener {
            ReviewDialog().show(supportFragmentManager, "AddReviewDialogFrag")


        }
        mainViewModel.getReview().observe(this@MainActivity, Observer {
            GlobalScope.launch {
                database.reviewDAO()
                    .insertReview(ReviewTable(0, it.name, it.rating, it.description, ""))



                getreviewList = getReviewFromDB()

                for (data in getreviewList) {

                    reviewList.add(Review(data.name, "30 Dec", data.comment, R.drawable.heart_icon, null))

                }

               // reviewAdapter = ReviewAdapter(reviewList, this@MainActivity)
                reviewAdapter.notifyDataSetChanged()
            }
        }

        )




        prepareReviewsWithImages()
        prepareReviews()
    }

    private fun prepareReviewsWithImages() {
        reviewImageList.add(ImageReview(R.drawable.model))
        reviewImageList.add(ImageReview(R.drawable.yup))
        reviewImageList.add(ImageReview(R.drawable.yo))

    }

    private fun addBannerImages(image: Int) {
        imageList.add(image)
    }

    private fun dummyBannerImages() {

        for (i in 1..3)
            addBannerImages(R.drawable.yo)

    }

    private fun prepareReviews() {

        for (i in 1..5) {
            reviewList.add(
                Review(
                    "Akshay",
                    "Dec 29",
                    getString(R.string.loreum),
                    R.drawable.model,
                    reviewImageList
                )
            )

        }
        reviewAdapter.notifyDataSetChanged()
    }

    suspend fun getReviewFromDB(): List<ReviewTable> {
         val   profileDao = database.reviewDAO().loadAllReviews();

        return profileDao
    }
}