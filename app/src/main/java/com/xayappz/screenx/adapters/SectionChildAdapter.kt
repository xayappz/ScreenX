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
import com.xayappz.screenx.utils.ItemSingleunSELECTNew
import com.xayappz.screenx.utils.unCHECKSELECTALL

class SectionChildAdapter(
    var ItemLongClickListener: ItemLongClickListenerNew,
    var listsectionData: ArrayList<String>,
    var SelectAll: Boolean,
    var ItemSingleSelectedNew: ItemSingleSelectedNew,
    var selectionEnabled: Boolean,
    var longPos: String,
    var ItemSingleunSELECTNew: ItemSingleunSELECTNew,
    var isOnlyunSelect: Boolean,
    var xxString: String,
    var unCHECKSELECTALL: unCHECKSELECTALL


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
        Log.d("SDSDAd", longPos + "SS")
        Log.d("selectionEnabled", selectionEnabled.toString() + "SS")
        Log.d("SelectAll", SelectAll.toString() + "SS")
        Log.d("xxString", xxString + "")
        holder.item_name.text = listsectionData.get(position)
        if (isOnlyunSelect) {


        }
        if (SelectAll) {
            holder.checkBox.isChecked = true
            holder.checkBox.visibility = View.VISIBLE
        } else {
            if (selectionEnabled && !xxString.isNullOrEmpty()) {

                if (!holder.item_name.text.equals(xxString)) {
                    holder.checkBox.isChecked = true
                    holder.checkBox.visibility = View.VISIBLE

                }else{
                    holder.checkBox.isChecked = false
                    holder.checkBox.visibility = View.VISIBLE

                }
            }

        }
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Log.d("CHECLED", isChecked.toString())
                ItemSingleSelectedNew.onSingleClickNEW(
                    holder.item_name.text.toString(),
                    position,
                    isChecked
                )
            } else {
                if (SelectAll) {
                    ItemSingleunSELECTNew.onSingleunClickNEW(
                        holder.item_name.text.toString(),
                        position
                    )
                } else {
                    holder.checkBox.isChecked = false
                    ItemSingleunSELECTNew.onSingleunClickNEW(
                        holder.item_name.text.toString(),
                        position
                    )
                }


            }

        }



        if (selectionEnabled && !longPos.isNullOrEmpty()) {
            Log.d("ASDdd", "gg")
            holder.checkBox.visibility = View.VISIBLE
            if (holder.item_name.text.equals(longPos)) {
                holder.checkBox.isChecked = true
            }
            holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    ItemSingleSelectedNew.onSingleClickNEW(
                        holder.item_name.text.toString(),
                        position,
                        isChecked
                    )
                } else {
                    holder.checkBox.isChecked = false
                    ItemSingleunSELECTNew.onSingleunClickNEW(
                        holder.item_name.text.toString(),
                        position
                    )
                }

            }

        }

//        if (SelectAll && longPos.isNotEmpty()) {
//            holder.checkBox.visibility = View.VISIBLE
//
//            if (holder.item_name.text.equals(longPos)) {
//                holder.checkBox.isChecked = true
//
//            }
//            if (isOnlyunSelect && selectionEnabled) {
//                holder.checkBox.visibility = View.VISIBLE
//
//            }
//
//        } else if (longPos.isNullOrEmpty() && SelectAll && !isOnlyunSelect) {
//            holder.checkBox.visibility = View.VISIBLE
//            holder.checkBox.isChecked = true
//
//        }
//        if (xxString.isNullOrEmpty()) {
//
//        }
//        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
//            Log.d("SelectAll:",SelectAll.toString())
//            ItemSingleSelectedNew.onSingleClickNEW(
//                holder.item_name.text.toString(),
//                position,
//                isChecked
//            )
//            if (!isChecked) {
//                ItemSingleunSELECTNew.onSingleunClickNEW(
//                    holder.item_name.text.toString(),
//                    position
//                )
//
//                //  holder.checkBox.visibility = View.VISIBLE
//            }
//
//
//           // notifyItemChanged(position)
//
//        }
//
//        Log.d("SSSSSSS", longPos.toString())
//        if (selectionEnabled) {
//            if (holder.item_name.text.toString() == longPos) {
//
//                holder.checkBox.isChecked = true
//            }
//            holder.checkBox.visibility = View.VISIBLE
//
//
//        }


        holder.itemView.setOnLongClickListener {
            holder.checkBox.isChecked = true
            holder.checkBox.visibility = View.VISIBLE
            Log.d("POSITION", position.toString())
            ItemLongClickListener.onLongItemClicked(
                true,
                holder.item_name.text.toString(),
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return listsectionData.size
    }
}