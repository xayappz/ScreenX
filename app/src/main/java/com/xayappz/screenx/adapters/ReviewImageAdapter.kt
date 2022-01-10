package com.xayappz.screenx.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.utils.DeleteImage


internal class ReviewImageAdapter
    (
    private var userId: String,
    private var count: Int,
    private var activity: Activity,
    private var deleteImage: DeleteImage

) :
    RecyclerView.Adapter<ReviewImageAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.imageReviewIV)
        var remove_rev_Img: ImageView = view.findViewById(R.id.imageView2)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        for (i in 1..count) {
            holder.image.setImageDrawable(activity.getDrawable(R.drawable.model))
        }
        holder.remove_rev_Img.setOnClickListener {
            var newImage = count - 1
            deleteImage.onCellDeleteImage(newImage.toString(), userId)
            count--
            notifyDataSetChanged()

        }

    }

    override fun getItemCount(): Int {
        return count

    }


}