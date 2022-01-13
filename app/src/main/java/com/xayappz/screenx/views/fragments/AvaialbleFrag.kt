package com.xayappz.screenx.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.adapters.CategoryItemAdapter
import com.xayappz.screenx.databinding.FragmentAvailableBinding
import com.xayappz.screenx.models.Items
import com.xayappz.screenx.models.Section
import com.xayappz.screenx.utils.AnyCheckinSelectAllMode
import com.xayappz.screenx.utils.LongPressListener
import com.xayappz.screenx.utils.isSelectedListener
import com.xayappz.screenx.viewmodels.ItemViewModel

 class AvailableFrag : Fragment(), isSelectedListener, LongPressListener, AnyCheckinSelectAllMode {
    lateinit var binding: FragmentAvailableBinding

    var listSections: ArrayList<Section> = ArrayList()
    lateinit var recyclerManager: RecyclerView.LayoutManager
    var data: ArrayList<Items> = ArrayList()
    lateinit var itemViewModel: ItemViewModel
    var getData: MutableList<String> = ArrayList()
    var alldata: ArrayList<String> = ArrayList()

    companion object {
        var isselectedAll: Boolean = false;
        var selectionMode: Boolean = false;
        var whichChecked: String = "";
        var MasterListener = false
        var toggleSelectionMode = false
    }

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
        itemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        loadDummyData()

        itemViewModel.getDataFromSelectedNEW().observe(requireActivity(), Observer {

            getData.clear()

            getData.addAll(it)
        })

        selectAllCheck()

        getItems()

        closeSelectionMode()
    }

    private fun closeSelectionMode() {
        binding.closeSelection.setOnClickListener {
            binding.hiddenItems.visibility = View.GONE
            binding.checkboxSelectAll.isChecked = false
            toggleSelectionMode = false
            selectionMode = false
            isselectedAll = false
            whichChecked = ""
            MasterListener = false
            itemViewModel.removeAllNEW()
            notifyList()
        }

    }

    private fun getItems() {
        binding.disableItem.setOnClickListener {

            if (itemViewModel.getSizeData() != null)
                if (itemViewModel.getSizeData()!! > 0) {
                    Toast.makeText(activity, getData.toString(), Toast.LENGTH_SHORT).show()

                }else
                {
                    Toast.makeText(activity, "Please select any item to delete", Toast.LENGTH_SHORT)
                        .show()
                }

        }
    }

    private fun selectAllCheck() {
        binding.checkboxSelectAll.setOnCheckedChangeListener { buttonView, isChecked ->
            isselectedAll = isChecked
            if (isselectedAll) {
                MasterListener = false
                whichChecked = ""
                for (Data in alldata) {

                    itemViewModel.addDataFromSelectedNEW(Data)
                }
            } else {
                if (!MasterListener) {
                    itemViewModel.removeAllNEW()

                }


            }
            notifyList()

        }
    }

    private fun loadDummyData() {
        recyclerManager = LinearLayoutManager(activity)
        var firstCategory = "Casual Wear"
        var secondCategory = "Winter Wear"
        var thirdCategory = "Summer Wear"


        var dataOne: ArrayList<String> = ArrayList();
        var dataTwo: ArrayList<String> = ArrayList();
        var dataThree: ArrayList<String> = ArrayList();


        dataOne.add("Casual Dress 1")
        dataOne.add("Casual Dress 2")
        dataOne.add("Casual Dress 3")


        dataTwo.add("Winter Dress 1")
        dataTwo.add("Winter Dress 2")
        dataTwo.add("Winter Dress 3")


        dataThree.add("Summer Dress 1")
        dataThree.add("Summer Dress 2")
        dataThree.add("Summer Dress 3")


        listSections.add(Section(firstCategory, dataOne))
        listSections.add(Section(secondCategory, dataTwo))
        listSections.add(Section(thirdCategory, dataThree))
        alldata.addAll(dataOne)
        alldata.addAll(dataTwo)
        alldata.addAll(dataThree)

        binding.itemRecycler.apply {
            adapter =
                CategoryItemAdapter(

                    listSections,
                    this@AvailableFrag,
                    this@AvailableFrag,
                    this@AvailableFrag

                )
            layoutManager = recyclerManager
        }

    }

    override fun isSelectedReponse(selected: Boolean, data: String) {
        if (selected) {
            itemViewModel.addDataFromSelectedNEW(data)
        } else {
            itemViewModel.removeDataFromSelectedNEW(data)
        }
    }

    override fun onLongItemClicked(): Boolean {
        selectionMode = true
        toggleSelectionMode = true
        showSelectionMode()
        notifyList()
        return true
    }

    private fun showSelectionMode() {
        binding.hiddenItems.visibility = View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyList() {
        binding.itemRecycler.adapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        selectionMode = false
        isselectedAll = false
        whichChecked = ""

    }

    override fun ItemClicked(data: String) {
        MasterListener = true
        itemViewModel.removeDataFromSelectedNEW(data)
        isselectedAll = false
        binding.checkboxSelectAll.isChecked = false
        whichChecked = data
    }


}