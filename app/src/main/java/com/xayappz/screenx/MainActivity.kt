package com.xayappz.screenx

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.xayappz.screenx.adapters.ReviewAdapter
import com.xayappz.screenx.adapters.ViewPageAdapter
import com.xayappz.screenx.db.Database
import com.xayappz.screenx.models.ReviewImage
import com.xayappz.screenx.utils.*
import com.xayappz.screenx.viewmodels.DialogViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.review_item.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ClickReview, DeleteReview, PassToActivity {
    lateinit var dialogViewModel: DialogViewModel
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var database: Database
    private val imageList = mutableListOf<Int>()

    private val reviewList: ArrayList<ReviewImage> = ArrayList<ReviewImage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database =
            Room.databaseBuilder(applicationContext, Database::class.java, "reviewDB")
                .fallbackToDestructiveMigration().build()
        dummyBannerImages()

        dialogViewModel = ViewModelProvider(
            this,
            DialogFactory(this.application)
        )[DialogViewModel::class.java]
        ViewPagerId.adapter = ViewPageAdapter(imageList)
        ViewPagerId.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        images_review_recyclerView
        indicatorId.setViewPager(ViewPagerId)


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
                        this@MainActivity
                    )


                    review_recyclerView.adapter = reviewAdapter
                    reviewAdapter.notifyDataSetChanged()
                    loadMore.visibility=View.GONE
                })
            }
        }


        GlobalScope.launch(Dispatchers.Main) {
            val x = async {
                dialogViewModel.getReviewByLimit().observe(this@MainActivity, Observer {
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
                        this@MainActivity

                    )


                    review_recyclerView.adapter = reviewAdapter
                    reviewAdapter.notifyDataSetChanged()
                })
            }

            x.await()


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
            addBannerImages(R.drawable.yo)

    }

    override fun onCellClickListener(data: String) {
        //
    }

    override fun onCellClickListener(data: String, userId: String) {
        //
    }




    override fun onCellDeleteImage(userId: String, data: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            dialogViewModel.deleteUserImage(userId, data)
            dialogViewModel.getReview().observe(this@MainActivity, Observer {


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
                    this@MainActivity
                )


                review_recyclerView.adapter = reviewAdapter
                reviewAdapter.notifyDataSetChanged()
            })
            reviewAdapter.notifyDataSetChanged()
        }
    }

    override fun onCellClickDelete(data: String) {

    }


}

