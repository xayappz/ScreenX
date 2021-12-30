package com.xayappz.screenx.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.models.ImageReview

internal class ReviewImageAdapter(private var moviesList: List<ImageReview>) :
    RecyclerView.Adapter<ReviewImageAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.imageReviewIV)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.image.setImageResource(moviesList.get(position).imageReview)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}