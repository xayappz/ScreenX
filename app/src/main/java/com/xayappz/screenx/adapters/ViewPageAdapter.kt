package com.xayappz.screenx.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R

class ViewPageAdapter(private val images: List<Int>) :
    RecyclerView.Adapter<ViewPageAdapter.Pager2ViewHolder>() {
    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageOfBanner: ImageView = itemView.findViewById(R.id.bannerImg)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewPageAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(p0.context).inflate(R.layout.item_page, p0, false)
        )
    }

    override fun onBindViewHolder(p0: ViewPageAdapter.Pager2ViewHolder, p1: Int) {
        p0.imageOfBanner.setImageResource(images[p1])
    }

    override fun getItemCount(): Int {
        return images.size
    }


}