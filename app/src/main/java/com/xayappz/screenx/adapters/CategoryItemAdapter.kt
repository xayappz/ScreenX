package com.xayappz.screenx.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.models.Section
import com.xayappz.screenx.utils.AnyCheckinSelectAllMode
import com.xayappz.screenx.utils.LongPressListener
import com.xayappz.screenx.utils.isSelectedListener

class CategoryItemAdapter(
    var listsection: ArrayList<Section>,
    var isSelectedListener: isSelectedListener?,
    var LongPressListener: LongPressListener?,
    var AnyCheckinSelectAllMode: AnyCheckinSelectAllMode?

) : RecyclerView.Adapter<CategoryItemAdapter.viewHolder>() {

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
        val sec: Section = listsection.get(position)
        val items: ArrayList<String> = ArrayList()
        items.addAll(sec.sectionItems)
        holder.recyclerSectionData.adapter =
            isSelectedListener?.let {
                AnyCheckinSelectAllMode?.let { it1 ->
                    LongPressListener?.let { it2 ->
                        AvailableItemAdapter(
                            listsectionData = items, isSelectedListener = it,
                            it2, it1
                        )
                    }
                }
            }
    }

    override fun getItemCount(): Int {
        return listsection.size
    }
}
