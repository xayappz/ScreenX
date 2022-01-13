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
    var listsectionData: ArrayList<String>,
    var isSelectedListener: isSelectedListener,
    var LongPressListener: LongPressListener,
    var AnyCheckinSelectAllMode: AnyCheckinSelectAllMode,

    ) : RecyclerView.Adapter<AvailableItemAdapter.viewHolder>() {
    var AllCheckedEnabled = false

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
        holder.item_name.text = listsectionData[position]

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
            holder.checkBox.isChecked = !holder.item_name.text.equals(AvailableFrag.whichChecked)
        }
        holder.itemView.setOnLongClickListener()
        {

            LongPressListener.onLongItemClicked()

        }

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            var productName = holder.item_name.text.toString()
            holder.checkBox.visibility = View.VISIBLE
            if (AllCheckedEnabled) {
                holder.checkBox.isChecked = false
                AnyCheckinSelectAllMode.ItemClicked(productName)

            } else {
                if (isChecked)
                    holder.checkBox.isChecked = true


            }
            isSelectedListener.isSelectedReponse(isChecked, productName)

        }
    }

    override fun getItemCount(): Int {
        return listsectionData.size
    }
}