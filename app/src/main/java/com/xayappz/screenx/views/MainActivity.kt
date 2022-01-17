package com.xayappz.screenx.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.xayappz.screenx.R
import com.xayappz.screenx.adapters.ReviewAdapter
import com.xayappz.screenx.adapters.ViewPageAdapter
import com.xayappz.screenx.db.Database
import com.xayappz.screenx.models.ReviewImage
import com.xayappz.screenx.utils.*
import com.xayappz.screenx.viewmodels.DialogViewModel
import kotlinx.android.synthetic.main.activity_item.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.review_item.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), ClickReview, DeleteReview, PassToActivity, ViewControl {
    lateinit var dialogViewModel: DialogViewModel
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var database: Database
    private val imageList = mutableListOf<Int>()
    private val reviewList: ArrayList<ReviewImage> = ArrayList<ReviewImage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(com.xayappz.screenx.R.layout.main_layout)


        database =
            Room.databaseBuilder(applicationContext, Database::class.java, "reviewDB")
                .fallbackToDestructiveMigration().build()
        //dummyBannerImages()
        appCompatButton2.setOnClickListener {
            val intent = Intent(this, ItemActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        }

        dialogViewModel = ViewModelProvider(
            this,
            DialogFactory(this.application)
        )[DialogViewModel::class.java]

        imageList.add(R.drawable.ak)
        imageList.add(R.drawable.yo)
        imageList.add(R.drawable.model)

        ViewPagerId.adapter = ViewPageAdapter(imageList)
        ViewPagerId.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        images_review_recyclerView
        indicatorId.setViewPager(ViewPagerId)
        ViewPagerId.autoScroll(3000)


        val layoutManager = LinearLayoutManager(applicationContext)

        review_recyclerView.layoutManager = layoutManager

        loadMore.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                dialogViewModel.getReview().observe(this@MainActivity, Observer {


                    reviewList.clear()

                    reviewList.addAll(it)
                    reviewAdapter = ReviewAdapter(
                        reviewList,
                        this@MainActivity,
                        this@MainActivity,
                        this@MainActivity,
                        this@MainActivity,
                        this@MainActivity

                    )


                    review_recyclerView.adapter = reviewAdapter
                    reviewAdapter.notifyDataSetChanged()
                    loadMore.visibility = View.GONE
                })
            }
        }


        GlobalScope.launch(Dispatchers.Main) {
            dialogViewModel.getReviewByLimit().observe(this@MainActivity, Observer {
                Log.d("SSjjj", it.size.toString())
                reviewList.clear()
                if (it.size > 4) {
                    loadMore.visibility = View.VISIBLE
                } else {
                    loadMore.visibility = View.GONE

                }
                reviewList.addAll(it)
                reviewAdapter = ReviewAdapter(
                    reviewList,
                    this@MainActivity,
                    this@MainActivity,
                    this@MainActivity,
                    this@MainActivity, this@MainActivity

                )


                review_recyclerView.adapter = reviewAdapter
                reviewAdapter.notifyDataSetChanged()
            })


        }
        add_reviewBn.setOnClickListener {
            if (!ReviewDialog().isVisible)
                ReviewDialog().show(supportFragmentManager, null)


        }

    }

    private fun addBannerImages(image: Int) {
        imageList.add(image)
    }

    private fun dummyBannerImages() {

        for (i in 1..3)
            addBannerImages(com.xayappz.screenx.R.drawable.ak)

    }

    override fun onCellClickListener(data: String) {
        //
    }

    override fun onCellClickListener(data: String, userId: String) {
        //
    }


    override fun onCellDeleteImage(userId: String, data: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            dialogViewModel.deleteUserImage(userId, data)
//            dialogViewModel.getReview().observe(this@MainActivity, Observer {
//
//
//                reviewList.clear()
//
//
//                if (it.size > 4) {
//                    loadMore.visibility = View.VISIBLE
//                } else {
//                    loadMore.visibility = View.GONE
//
//                }
//                reviewList.addAll(it)
//                reviewAdapter = ReviewAdapter(
//                    reviewList,
//                    this@MainActivity,
//                    this@MainActivity,
//                    this@MainActivity,
//                    this@MainActivity
//                )
//
//
//                review_recyclerView.adapter = reviewAdapter
//                reviewAdapter.notifyDataSetChanged()
//            })
        }
    }

    override fun onCellClickDelete(data: String) {

    }

    override fun onViewHandle(data: Boolean) {
//        lifecycleScope.launch(Dispatchers.Main)
//        {
//            dialogViewModel.getReview()
//
//        }
    }
    fun ViewPager2.autoScroll(interval: Long) {

        val handler = Handler()
        var scrollPosition = 0

        val runnable = object : Runnable {

            override fun run() {

                val count = adapter?.itemCount ?: 0
                setCurrentItem(scrollPosition++ % count, true)

                handler.postDelayed(this, interval)
            }
        }
        (object: ViewPager.OnPageChangeListener {        override fun onPageSelected(position: Int) {
            // Updating "scroll position" when user scrolls manually
            scrollPosition = position + 1
        }

            override fun onPageScrollStateChanged(state: Int) {
                // Not necessary
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
        })

        handler.post(runnable)
    }

}
