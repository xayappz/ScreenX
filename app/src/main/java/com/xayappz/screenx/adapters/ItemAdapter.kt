package com.xayappz.screenx.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.databinding.ItemCategoryBinding
import com.xayappz.screenx.models.Items
import com.xayappz.screenx.utils.ItemLongClickListener
import com.xayappz.screenx.utils.SelectAllListener
import com.xayappz.screenx.utils.UnSelectAllListener
import kotlinx.android.synthetic.main.item_category.view.*


class ItemAdapter(
    private val data: List<Items>,
    private val itemClick: ItemLongClickListener,
    private val isChecked: Boolean,
    private val unSelectAllListener: UnSelectAllListener
) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>(), SelectAllListener {

    inner class MyViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bindingItems: Items, itemClick: ItemLongClickListener) {
            binding.item = bindingItems
            binding.executePendingBindings()
            itemView.setOnLongClickListener() {
                itemClick.onLongItemClicked(bindingItems)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var inflator = LayoutInflater.from(parent.context)
        val listItemBinding = ItemCategoryBinding.inflate(inflator, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position], itemClick)
        if (isChecked) {
            holder.itemView.checkBox.visibility = View.VISIBLE
            holder.itemView.checkBox.isChecked = true
            holder.itemView.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                unSelectAllListener.onAllUnSelectClicked(true)
            }

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onAllSelectClicked(allSelected: Boolean) {
        notifyDataSetChanged()
    }


}