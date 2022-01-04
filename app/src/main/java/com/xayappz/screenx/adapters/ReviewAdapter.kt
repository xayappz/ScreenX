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
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.models.Images
import com.xayappz.screenx.models.ReviewImage
import com.xayappz.screenx.utils.*


internal class ReviewAdapter(
    private var itemsList: ArrayList<ReviewImage>,
    private var activity: Activity,
    var reviewClick: ClickReview,
    var deleteReview: DeleteReview,
    var passToActivity: PassToActivity

) : RecyclerView.Adapter<ReviewAdapter.MyViewHolder>(), ClickReview, DeleteImage {
    private var isVisibleImages = true

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameofReviewer: TextView = view.findViewById(R.id.reviewwenameTxt)
        var description: TextView = view.findViewById(R.id.reviewTxt)
        var card: CardView = view.findViewById(R.id.cardView)
        var date: TextView = view.findViewById(R.id.dateTxt)
        var userImage: ImageView = view.findViewById(R.id.profile_image)
        var reviewImages: RecyclerView = view.findViewById(R.id.images_review_recyclerView)
        var rating: RatingBar = view.findViewById(R.id.ratingBar)
        var imageShow: ImageView = view.findViewById(R.id.imageView5)
        var profile_image: ImageView = view.findViewById(R.id.profile_image)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var images: ArrayList<Images> = ArrayList()
        var reviewImageAdapter: ReviewImageAdapter
        val layoutManagerImage = LinearLayoutManager(activity)
        holder.profile_image.setImageDrawable(activity.getDrawable(R.drawable.model))
        holder.nameofReviewer.text = itemsList.get(position).name
        holder.description.text = itemsList.get(position).description
        holder.date.text = itemsList.get(position).date
        holder.rating.rating = itemsList.get(position).rating
        holder.reviewImages.layoutManager = layoutManagerImage
        layoutManagerImage.orientation = LinearLayoutManager.HORIZONTAL

        holder.card.setOnClickListener {
            reviewClick.onCellClickListener(itemsList.get(position).id.toString())
            ReviewDialog().show(
                (activity as AppCompatActivity).supportFragmentManager,
                itemsList.get(position).id.toString()
            )

        }

        var count = itemsList.get(position).image
        if (count > 0) {
            holder.imageShow.visibility = View.VISIBLE
        } else {
            holder.imageShow.visibility = View.GONE

        }
        holder.imageShow.setOnClickListener {
            Log.d("IDDDD", itemsList.get(position).id.toString())
            images.clear()
            if (isVisibleImages) {
                Log.d("AAA", isVisibleImages.toString())
                holder.reviewImages.visibility = View.VISIBLE
                holder.imageShow.setImageResource(R.drawable.ic_baseline_minimize_24)
                isVisibleImages = false

            } else {
                Log.d("BBB", isVisibleImages.toString())
                holder.imageShow.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
                isVisibleImages = true
                holder.reviewImages.visibility = View.GONE


            }

            if (count > 0) {
                reviewImageAdapter =
                    ReviewImageAdapter(itemsList.get(position).id.toString(), count, activity, this)
                holder.reviewImages.adapter = reviewImageAdapter
                reviewImageAdapter.notifyDataSetChanged()

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

    override fun onCellDeleteImage(userId: String, data: String) {
        Log.d("imagee", data.toString())
        Log.d("userId", userId.toString())
        passToActivity.onCellDeleteImage(data, userId)
    }

}