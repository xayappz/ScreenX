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
import com.xayappz.screenx.models.ReviewImage
import com.xayappz.screenx.utils.ReviewDialog


internal class ReviewAdapter(
    private var itemsList: ArrayList<ReviewImage>,
    private var activity: Activity
) :
    RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {
    private lateinit var reviewImageAdapter: ReviewImageAdapter

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameofReviewer: TextView = view.findViewById(R.id.reviewwenameTxt)
        var description: TextView = view.findViewById(R.id.reviewTxt)
        var date: TextView = view.findViewById(R.id.dateTxt)
        var userImage: ImageView = view.findViewById(R.id.profile_image)
        var reviewImages: RecyclerView = view.findViewById(R.id.images_review_recyclerView)
        var rating: RatingBar = view.findViewById(R.id.ratingBar)

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

        Log.d("SDAd", itemsList.get(position).name.toString() + "")
        holder.nameofReviewer.setOnClickListener {
            ReviewDialog().show(
                (activity as AppCompatActivity).supportFragmentManager,
                "EditReviewDialogFrag"
            )

        }

//        if (itemsList[position].reviewImages?.isNotEmpty() == true && position % 2 == 0) {
//            holder.reviewImages.visibility = View.VISIBLE
//            reviewImageAdapter = itemsList[position].reviewImages?.let { ReviewImageAdapter(it) }!!
//            holder.reviewImages.adapter = reviewImageAdapter
//
//
//        } else {
//            holder.reviewImages.visibility = View.GONE
//
//        }
//

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}