package com.xayappz.screenx.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.adapters.CategoryItemAdapter
import com.xayappz.screenx.databinding.FragmentAvailableBinding
import com.xayappz.screenx.models.Section
import com.xayappz.screenx.utils.AnyCheckinSelectAllMode
import com.xayappz.screenx.utils.LongPressListener
import com.xayappz.screenx.utils.isSelectedListener
import com.xayappz.screenx.viewmodels.ItemViewModel

class AvailableFrag : Fragment(), isSelectedListener, LongPressListener, AnyCheckinSelectAllMode {
    private lateinit var binding: FragmentAvailableBinding

    private var listSections: ArrayList<Section> = ArrayList()
    private lateinit var recyclerManager: RecyclerView.LayoutManager
    private lateinit var itemViewModel: ItemViewModel
    private var getData: MutableList<String> = ArrayList()
    private var alldata: ArrayList<String> = ArrayList()

    companion object {
        var isselectedAll: Boolean = false
        var selectionMode: Boolean = false
        var whichChecked: String = ""
        var MasterListener = false
        var toggleSelectionMode = false
        var longPressItem = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAvailableBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val x: ItemViewModel by activityViewModels()
        loadDummyData()
        itemViewModel = x


        itemViewModel.getDataFromSelectedNEW().observe(requireActivity(), {

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
            longPressItem = ""
            itemViewModel.removeAllNEW()
            notifyList()
        }

    }

    private fun getItems() {
        binding.disableItem.setOnClickListener {

            if (itemViewModel.getSizeData() != null)
                if (itemViewModel.getSizeData()!! > 0) {
                    Toast.makeText(activity, getData.toString(), Toast.LENGTH_SHORT).show()

                } else if (itemViewModel.getSizeData() == 0 || itemViewModel.getSizeData() == null) {
                    Toast.makeText(activity, getString(R.string.delete_text), Toast.LENGTH_SHORT)
                        .show()
                }

        }
    }

    private fun selectAllCheck() {
        binding.checkboxSelectAll.setOnCheckedChangeListener { _, isChecked ->
            isselectedAll = isChecked
            if (isselectedAll) {
                MasterListener = false
                whichChecked = ""
                for (Data in alldata) {

                    itemViewModel.addDataFromSelectedNEW(Data)
                }
            } else {
                longPressItem = ""
                if (!MasterListener) {
                    itemViewModel.removeAllNEW()

                }


            }
            notifyList()

        }
    }

    private fun loadDummyData() {
        recyclerManager = LinearLayoutManager(activity)
        val firstCategory = "Casual Wear"
        val secondCategory = "Winter Wear"
        val thirdCategory = "Summer Wear"


        val dataOne: ArrayList<String> = ArrayList()
        val dataTwo: ArrayList<String> = ArrayList()
        val dataThree: ArrayList<String> = ArrayList()


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

    override fun onLongItemClicked(Item: String): Boolean {
        selectionMode = true
        toggleSelectionMode = true
        longPressItem = Item
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
        longPressItem = ""
    }

    override fun ItemClicked(data: String) {
        MasterListener = true
        itemViewModel.removeDataFromSelectedNEW(data)
        isselectedAll = false
        binding.checkboxSelectAll.isChecked = false
        whichChecked = data
    }


}