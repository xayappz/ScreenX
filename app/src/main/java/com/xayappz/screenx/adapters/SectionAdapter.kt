package com.xayappz.screenx.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.models.Section
import com.xayappz.screenx.utils.ItemLongClickListenerNew
import com.xayappz.screenx.utils.ItemSingleSelectedNew

class SectionAdapter(
    var ItemLongClickListener: ItemLongClickListenerNew,
    var SelectAll: Boolean,
    var listsection: ArrayList<Section>,
    var ItemSingleSelectedNew: ItemSingleSelectedNew


) : RecyclerView.Adapter<SectionAdapter.viewHolder>(), ItemLongClickListenerNew,
    ItemSingleSelectedNew {

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sectionName: TextView = itemView.findViewById(R.id.categoryName)
        var recyclerSectionData: RecyclerView = itemView.findViewById(R.id.recyclerSectionData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.section_item, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        holder.sectionName.text = listsection.get(position).sectionName
        var sec: Section
        sec = listsection.get(position)
        var items: ArrayList<String> = ArrayList()
        items.addAll(sec.sectionItems)
        Log.d("SOBUA", items.size.toString() + "SS")

        holder.recyclerSectionData.adapter = SectionChildAdapter(this, items, SelectAll,this)
    }

    override fun getItemCount(): Int {
        return listsection.size
    }


    override fun onLongItemClicked(longPressed: Boolean): Boolean {
        ItemLongClickListener.onLongItemClicked(true)
        return true
    }

    override fun onSingleClickNEW(data: String) {
        ItemSingleSelectedNew.onSingleClickNEW(data)
    }

}