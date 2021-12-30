package com.xayappz.screenx.adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.models.Review

internal class ReviewAdapter(private var itemsList: List<Review>, private var activity: Activity) :
    RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {
    private lateinit var reviewImageAdapter: ReviewImageAdapter

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameofReviewer: TextView = view.findViewById(R.id.reviewwenameTxt)
        var description: TextView = view.findViewById(R.id.reviewTxt)
        var date: TextView = view.findViewById(R.id.dateTxt)
        var userImage: ImageView = view.findViewById(R.id.profile_image)
        var reviewImages: RecyclerView = view.findViewById(R.id.images_review_recyclerView)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val layoutManagerImage = LinearLayoutManager(activity)

        holder.nameofReviewer.text = itemsList[position].name
        holder.description.text = itemsList[position].details
        holder.date.text = itemsList[position].date
        holder.userImage.setImageResource(itemsList[position].image)
        holder.reviewImages.layoutManager = layoutManagerImage
        layoutManagerImage.orientation = LinearLayoutManager.HORIZONTAL
        if (itemsList[position].reviewImages?.isNotEmpty() == true && position % 2 == 0) {
            holder.reviewImages.visibility = View.VISIBLE
            reviewImageAdapter = itemsList[position].reviewImages?.let { ReviewImageAdapter(it) }!!
            holder.reviewImages.adapter = reviewImageAdapter


        } else {
            holder.reviewImages.visibility = View.GONE

        }
//

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}