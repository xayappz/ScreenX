package com.xayappz.screenx.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.adapters.ItemAdapter
import com.xayappz.screenx.databinding.FragmentAvailableBinding
import com.xayappz.screenx.models.Items
import com.xayappz.screenx.utils.ItemLongClickListener
import com.xayappz.screenx.utils.UnSelectAllListener

class AvailableFragment : Fragment(), ItemLongClickListener,UnSelectAllListener{
    lateinit var binding: FragmentAvailableBinding
    lateinit var recyclerManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAvailableBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = listOf<Items>(
            Items(
                "1",
                getString(R.string.card_text)
            ),
            Items(
                "2",
                getString(R.string.card_text)
            ),
            Items(
                "3",
                getString(R.string.card_text)
            ),
            Items(
                "4",
                getString(R.string.card_text)
            ),
            Items(
                "5",
                getString(R.string.card_text)
            )
        )

        recyclerManager = LinearLayoutManager(activity)
        binding.itemRecycler.apply {
            adapter = ItemAdapter(data, this@AvailableFragment, false,this@AvailableFragment)
            layoutManager = recyclerManager

        }


        binding.checkboxSelectAll.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.itemRecycler.adapter = ItemAdapter(data, this@AvailableFragment, isChecked,this@AvailableFragment)

        }
    }

    override fun onLongItemClicked(items: Items): Boolean {
        binding.hiddenItems.visibility = View.VISIBLE
        return true
    }

    override fun onAllUnSelectClicked(allSelected: Boolean) {
        binding.hiddenItems.visibility = View.GONE
        binding.checkboxSelectAll.isChecked=false
    }
}