package com.xayappz.screenx.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.adapters.DisabledAdapter
import com.xayappz.screenx.viewmodels.ItemViewModel
import kotlinx.android.synthetic.main.fragment_disabled.*

class DisabledFragment : Fragment() {
    val disabledItems: ArrayList<String> = ArrayList()
    private lateinit var recyclerManager: RecyclerView.LayoutManager
    private val x by activityViewModels<ItemViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_disabled,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerManager = LinearLayoutManager(activity)

        disabledRV.apply {
            adapter =
                DisabledAdapter(
                    disabledItems

                )
            layoutManager = recyclerManager
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.VISIBLE
        if (x.getSizeData() == 0 || x.getSizeData() == null) {
            noData.visibility = View.VISIBLE
        } else {
            noData.visibility = View.GONE
        }
        x.getDataFromSelectedNEW().observe(requireActivity(), {
            disabledItems.clear()
            disabledItems.addAll(it)
            disabledRV.adapter?.notifyDataSetChanged()
            progressBar.visibility = View.GONE
        })


    }


}