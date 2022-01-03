package com.xayappz.screenx.adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.models.Images
import com.xayappz.screenx.models.ReviewImage
import com.xayappz.screenx.utils.ClickReview
import com.xayappz.screenx.utils.DeleteReview
import com.xayappz.screenx.utils.ReviewDialog
import com.xayappz.screenx.viewmodels.DialogViewModel


internal class ReviewAdapter(
    private var itemsList: ArrayList<ReviewImage>,
    private var reviewListImages: ArrayList<Images>,
    private var activity: Activity,
    var reviewClick: ClickReview,
    var deleteReview: DeleteReview
) : RecyclerView.Adapter<ReviewAdapter.MyViewHolder>(),ClickReview {
    private var isVisibleImages = false
    private lateinit var reviewImageAdapter: ReviewImageAdapter
    private var images: ArrayList<Images> = ArrayList()

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameofReviewer: TextView = view.findViewById(R.id.reviewwenameTxt)
        var description: TextView = view.findViewById(R.id.reviewTxt)
        var date: TextView = view.findViewById(R.id.dateTxt)
        var userImage: ImageView = view.findViewById(R.id.profile_image)
        var reviewImages: RecyclerView = view.findViewById(R.id.images_review_recyclerView)
        var rating: RatingBar = view.findViewById(R.id.ratingBar)
        var imageShow: ImageView = view.findViewById(R.id.imageView5)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val layoutManagerImage = LinearLayoutManager(activity)

        holder.nameofReviewer.text = itemsList.get(position).name
        holder.description.text = itemsList.get(position).description
        holder.date.text = itemsList.get(position).date
        holder.rating.rating = itemsList.get(position).rating
        holder.userImage.setImageResource(itemsList.get(position).image)
        holder.reviewImages.layoutManager = layoutManagerImage
        layoutManagerImage.orientation = LinearLayoutManager.HORIZONTAL

        holder.nameofReviewer.setOnClickListener {
            reviewClick.onCellClickListener(itemsList.get(position).id.toString())
            ReviewDialog().show(
                (activity as AppCompatActivity).supportFragmentManager,
                itemsList.get(position).id.toString()
            )

        }
        holder.imageShow.setOnClickListener {
            images.clear()
            if (!isVisibleImages) {
            holder.reviewImages.visibility = View.VISIBLE

            holder.imageShow.setImageResource(R.drawable.ic_baseline_minimize_24)
            isVisibleImages = true

        } else {
            holder.reviewImages.visibility = View.GONE
            holder.imageShow.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
            isVisibleImages = false


        }
            var userId = itemsList[position].userId
            for (data in reviewListImages) {
                if (data.userId.equals(userId)) {
                    images.add(Images(data.userId, data.imageBmp))
                    reviewImageAdapter = ReviewImageAdapter(null, images!!, this)
                    holder.reviewImages.adapter = reviewImageAdapter

                }

            }


        }


    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onCellClickListener(data: String) {
        deleteReview.onCellClickDelete(data)
    }

    override fun onCellClickListener(data: String, userId: String) {
        TODO("Not yet implemented")
    }

}