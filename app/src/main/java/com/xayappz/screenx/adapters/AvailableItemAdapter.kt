package com.xayappz.screenx.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.utils.AnyCheckinSelectAllMode
import com.xayappz.screenx.utils.LongPressListener
import com.xayappz.screenx.utils.isSelectedListener
import com.xayappz.screenx.views.fragments.AvailableFrag

class AvailableItemAdapter(
    private var listsectionData: ArrayList<String>,
    private var isSelectedListener: isSelectedListener?,
    private var LongPressListener: LongPressListener?,
    private var AnyCheckinSelectAllMode: AnyCheckinSelectAllMode?,

    ) : RecyclerView.Adapter<AvailableItemAdapter.ViewHolder>() {
    private var AllCheckedEnabled = false

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.item_name)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_category_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = listsectionData[position]

        if (AvailableFrag.whichChecked.isEmpty()) {
            if (AvailableFrag.selectionMode) {
                holder.checkBox.visibility = View.VISIBLE
            }

            if (AvailableFrag.isselectedAll && AvailableFrag.selectionMode) {
                holder.checkBox.visibility = View.VISIBLE
                holder.checkBox.isChecked = true
                AllCheckedEnabled = true
            }


        } else {
            holder.checkBox.visibility = View.VISIBLE
            holder.checkBox.isChecked = !holder.name.text.equals(AvailableFrag.whichChecked)
        }
        holder.itemView.setOnLongClickListener()
        {

            LongPressListener!!.onLongItemClicked(holder.name.text.toString())

        }
        if (AvailableFrag.selectionMode) {

            holder.itemView.setOnClickListener {
                holder.checkBox.performClick()
            }

        }
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            val productName = holder.name.text.toString()
            holder.checkBox.visibility = View.VISIBLE
            if (AllCheckedEnabled) {
                holder.checkBox.isChecked = false
                AnyCheckinSelectAllMode!!.ItemClicked(productName)

            } else {
                if (isChecked)
                    holder.checkBox.isChecked = true
            }
            isSelectedListener!!.isSelectedReponse(isChecked, productName)

        }
        if (AvailableFrag.longPressItem.equals(holder.name.text.toString())) {
            holder.checkBox.isChecked = true
        }

    }

    override fun getItemCount(): Int {
        return listsectionData.size
    }
}