package com.xayappz.screenx.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.models.Review

internal class ReviewAdapter(private var itemsList: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameofReviewer: TextView = view.findViewById(R.id.reviewwenameTxt)
        var description: TextView = view.findViewById(R.id.reviewTxt)
        var date: TextView = view.findViewById(R.id.dateTxt)
        var userImage: ImageView = view.findViewById(R.id.profile_image)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameofReviewer.text = itemsList.get(position).name
        holder.description.text = itemsList.get(position).details
        holder.date.text = itemsList.get(position).date
        holder.userImage.setImageResource(itemsList[position].image)

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}