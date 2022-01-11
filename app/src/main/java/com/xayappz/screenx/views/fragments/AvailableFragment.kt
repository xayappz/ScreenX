package com.xayappz.screenx.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.adapters.ItemAdapter
import com.xayappz.screenx.adapters.SectionAdapter
import com.xayappz.screenx.databinding.ABinding
import com.xayappz.screenx.models.Items
import com.xayappz.screenx.models.Section
import com.xayappz.screenx.utils.*
import com.xayappz.screenx.viewmodels.ItemViewModel
import kotlinx.android.synthetic.main.a.*

class AvailableFragment : Fragment(), ItemLongClickListener, UnSelectAllListener,
    SelectedSingleListener, unSelectedSingleListener, ItemLongClickListenerNew,ItemSingleSelectedNew {
    lateinit var binding: ABinding
    var listSections: ArrayList<Section> = ArrayList()

    lateinit var recyclerManager: RecyclerView.LayoutManager
    var data: ArrayList<Items> = ArrayList()
    var datafromAdapterAll: MutableList<String> = ArrayList()
    var datafromAdapter: MutableList<String> = ArrayList()

    var datafromAdapterNEW: MutableList<String> = ArrayList()
    var allData: MutableList<String> = ArrayList()

    private var isAllSelected: Boolean = false
    private var isFromSingle: Boolean = false
    private var selectionEnabled: Boolean = false
    lateinit var itemViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ABinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("SS", "SASDdsa")
        itemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        loadDummyData()
        loadSections()

        itemViewModel.getDataFromSelected().observe(requireActivity(), Observer {
            datafromAdapter.clear()
            datafromAdapter.addAll(it)

        })

        itemViewModel.getDataFromSelectedNEW().observe(requireActivity(), Observer {
            datafromAdapterNEW.clear()
            datafromAdapterNEW.addAll(it)

        })


        binding.closeSelection.setOnClickListener {
            selectionEnabled = false
            isFromSingle = false
            closeSelection.visibility = View.GONE
            checkbox_select_all.visibility = View.GONE
            disableItem.visibility = View.GONE

            binding.checkboxSelectAll.isChecked = false
            binding.itemRecycler.adapter = ItemAdapter(
                data,
                this@AvailableFragment,
                isChecked = false,
                isAnyUnchecked = false,
                isSelectAll = false,
                unSelectAllListener = this@AvailableFragment,
                selectionEnabled = selectionEnabled,
                singleListener = this,
                unsingleListener = this@AvailableFragment
            )


        }

//        recyclerManager = LinearLayoutManager(activity)
//        binding.itemRecycler.apply {
//            adapter =
//                ItemAdapter(
//                    data,
//                    this@AvailableFragment,
//                    isChecked = false,
//                    isAnyUnchecked = false,
//                    isSelectAll = false,
//                    unSelectAllListener = this@AvailableFragment,
//                    selectionEnabled = selectionEnabled,
//                    singleListener = this@AvailableFragment,
//                    unsingleListener = this@AvailableFragment
//                )
//            layoutManager = recyclerManager
//
//        }


        binding.checkboxSelectAll.setOnCheckedChangeListener { buttonView, isChecked ->
            isAllSelected = isChecked
//            if (!isAllSelected) {
//                itemViewModel.removeAll()
//
//            }
//            binding.itemRecycler.adapter =
//                ItemAdapter(
//                    data,
//                    this@AvailableFragment,
//                    false,
//                    isAnyUnchecked = false,
//                    isSelectAll = isChecked,
//                    unSelectAllListener = this@AvailableFragment,
//                    selectionEnabled = selectionEnabled,
//                    singleListener = this@AvailableFragment,
//                    unsingleListener = this@AvailableFragment
//                )

            binding.itemRecycler.apply {
                adapter =
                    SectionAdapter(
                        this@AvailableFragment,
                        isAllSelected,
                        listSections,
                        this@AvailableFragment,

                        )
                layoutManager = recyclerManager
            }

        }
        binding.disableItem.setOnClickListener {
            if (isAllSelected) {
                for (Data in data) {
                    datafromAdapterAll.add(Data.itemName)
                }



                Toast.makeText(activity, allData.toString(), Toast.LENGTH_SHORT).show()
                datafromAdapterAll.clear()
            } else {
                if (isFromSingle) {
                    if (datafromAdapterNEW.size > 0)
                        Toast.makeText(activity, datafromAdapterNEW.toString(), Toast.LENGTH_SHORT)
                            .show()

                }
            }
        }
    }

    private fun loadSections() {
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
        dataTwo.add("Winter Dress 3")
        dataTwo.add("Winter Dress 3")


        dataThree.add("Summer Dress 1")
        dataThree.add("Summer Dress 2")
        dataThree.add("Summer Dress 3")

        allData.addAll(dataOne)
        allData.addAll(dataTwo)
        allData.addAll(dataThree)

        listSections.add(Section(firstCategory, dataOne))
        listSections.add(Section(secondCategory, dataTwo))
        listSections.add(Section(thirdCategory, dataThree))

        Log.d("listsections", listSections.toString())

        binding.itemRecycler.apply {
            adapter =
                SectionAdapter(
                    this@AvailableFragment,
                    false,
                    listSections,
                    this@AvailableFragment,

                    )
            layoutManager = recyclerManager
        }

    }


    private fun loadDummyData() {

        for (i in 1..9) {

            var classObject = Items()
            classObject.itemName = getString(R.string.card_text) + i
            classObject.id = i.toString()
            data.add(classObject)
        }


    }

    override fun onLongItemClicked(items: Items): Boolean {
        selectionEnabled = true
        closeSelection.visibility = View.VISIBLE
        checkbox_select_all.visibility = View.VISIBLE
        disableItem.visibility = View.VISIBLE
        binding.itemRecycler.adapter = ItemAdapter(
            data,
            this@AvailableFragment,
            isChecked = true,
            isAnyUnchecked = false,
            isSelectAll = false,
            unSelectAllListener = this@AvailableFragment,
            selectionEnabled = selectionEnabled,
            singleListener = this@AvailableFragment,
            unsingleListener = this@AvailableFragment
        )
        return true
    }

    override fun onAllUnSelectClicked(allSelected: Boolean) {
        isAllSelected = false

        binding.checkboxSelectAll.isChecked = false
        binding.itemRecycler.adapter = ItemAdapter(
            data,
            this@AvailableFragment,
            isChecked = false,
            isAnyUnchecked = true,
            isSelectAll = false,
            unSelectAllListener = this@AvailableFragment,
            selectionEnabled = selectionEnabled,
            singleListener = this@AvailableFragment,
            unsingleListener = this@AvailableFragment
        )


    }

    override fun onSingleSelected(data: String) {
        if (!isAllSelected) {
            isFromSingle = true
            itemViewModel.addDataFromSelected(data)
        } else {
            isFromSingle = false
        }
    }


    override fun onUnSingleSelected(data: String) {
        itemViewModel.removeDataFromSelected(data)
    }

    override fun onLongItemClicked(longPressed: Boolean): Boolean {
        selectionEnabled = true
        closeSelection.visibility = View.VISIBLE
        checkbox_select_all.visibility = View.VISIBLE
        disableItem.visibility = View.VISIBLE

        return true
    }



    override fun onSingleClickNEW(data: String) {
        if (!isAllSelected) {
            isFromSingle = true
            itemViewModel.addDataFromSelectedNEW(data)
        } else {
            isFromSingle = false
        }    }


}