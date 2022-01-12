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
import com.xayappz.screenx.utils.ItemSingleunSELECTNew
import com.xayappz.screenx.utils.unCHECKSELECTALL
import com.xayappz.screenx.views.fragments.AvailableFragment

class SectionAdapter(
    var ItemLongClickListener: ItemLongClickListenerNew,
    var SelectAll: Boolean,
    var listsection: ArrayList<Section>,
    var selectionEnabled: Boolean,
    var isOnlyunSelect: Boolean,
    var xxString: String,
    var ItemSingleSelectedNew: ItemSingleSelectedNew,
    var longPos: String,
    var ItemSingleunSELECTNew: ItemSingleunSELECTNew,
    var unCHECKSELECTALL: unCHECKSELECTALL


) : RecyclerView.Adapter<SectionAdapter.viewHolder>(), ItemLongClickListenerNew,
    ItemSingleSelectedNew, ItemSingleunSELECTNew, unCHECKSELECTALL {

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
        SelectAll = AvailableFragment.selectedAll
        holder.recyclerSectionData.adapter =
            SectionChildAdapter(
                this,
                items,
                SelectAll,
                this,
                selectionEnabled,
                longPos,
                this,
                isOnlyunSelect,
                xxString,
                unCHECKSELECTALL
            )
    }

    override fun getItemCount(): Int {
        return listsection.size
    }


    override fun onLongItemClicked(longPressed: Boolean, pos: String, intpos: Int): Boolean {
        Log.d("SSSSSadasdsad", pos.toString())
        ItemLongClickListener.onLongItemClicked(true, pos, intpos)
        selectionEnabled = true
        longPos = pos
        //notifyItemChanged(intpos)
        notifyDataSetChanged()
        return true
    }

    override fun onSingleClickNEW(data: String, pos: Int, state: Boolean) {
        Log.d("VVCXZ", data)
        ItemSingleSelectedNew.onSingleClickNEW(data, pos, state)
    }

    override fun onSingleunClickNEW(data: String, position: Int) {
        ItemSingleunSELECTNew.onSingleunClickNEW(data, position)
        xxString = data
        Log.d("SADa", xxString)
        // notifyItemChanged(position)

    }

    override fun onunCHECKSELECTALL() {
        unCHECKSELECTALL.onunCHECKSELECTALL()
    }


}