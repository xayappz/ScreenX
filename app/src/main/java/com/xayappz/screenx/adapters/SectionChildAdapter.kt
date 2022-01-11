package com.xayappz.screenx.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.utils.ItemLongClickListenerNew
import com.xayappz.screenx.utils.ItemSingleSelectedNew

class SectionChildAdapter(
    var ItemLongClickListener: ItemLongClickListenerNew,
    var listsectionData: ArrayList<String>,
    var SelectAll: Boolean,
    var ItemSingleSelectedNew: ItemSingleSelectedNew


) : RecyclerView.Adapter<SectionChildAdapter.viewHolder>() {

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_name: TextView = itemView.findViewById(R.id.item_name)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_category_item, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        Log.d("SDSDAd", listsectionData.get(position) + "SS")
        holder.item_name.text = listsectionData.get(position)
        if (SelectAll) {
            holder.checkBox.visibility = View.VISIBLE
            holder.checkBox.isChecked = true


        } else {
            holder.checkBox.visibility = View.GONE
            holder.checkBox.isChecked = false

        }
        holder.itemView.setOnClickListener {
            ItemSingleSelectedNew.onSingleClickNEW(holder.item_name.toString())
        }
        holder.itemView.setOnLongClickListener() {
            holder.checkBox.visibility = View.VISIBLE
            holder.checkBox.isChecked = true
            ItemLongClickListener.onLongItemClicked(true)

        }

    }

    override fun getItemCount(): Int {
        return listsectionData.size
    }
}