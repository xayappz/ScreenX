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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.adapters.ItemAdapter
import com.xayappz.screenx.databinding.FragmentAvailableBinding
import com.xayappz.screenx.models.Items
import com.xayappz.screenx.utils.ItemLongClickListener
import com.xayappz.screenx.utils.SelectedSingleListener
import com.xayappz.screenx.utils.UnSelectAllListener
import com.xayappz.screenx.utils.unSelectedSingleListener
import com.xayappz.screenx.viewmodels.ItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AvailableFragment : Fragment(), ItemLongClickListener, UnSelectAllListener,
    SelectedSingleListener, unSelectedSingleListener {
    lateinit var binding: FragmentAvailableBinding
    lateinit var recyclerManager: RecyclerView.LayoutManager
    lateinit var data: List<Items>

    var datafromAdapter: MutableList<String> = ArrayList()
    private var isAllSelected: Boolean = false
    private var isFromSingle: Boolean = false
    private var selectionEnabled: Boolean = false
    lateinit var itemViewModel: ItemViewModel

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

        lifecycleScope.launch(Dispatchers.Main)
        {
            itemViewModel.getDataFromSelected().observe(requireActivity(), Observer {
                Log.d("ITTTTT",it.size.toString())
                datafromAdapter.clear()
                datafromAdapter.addAll(it)

            })


        }
        binding.closeSelection.setOnClickListener {
            selectionEnabled = false
            isFromSingle = false
            binding.hiddenItems.visibility = View.GONE
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

        recyclerManager = LinearLayoutManager(activity)
        binding.itemRecycler.apply {
            adapter =
                ItemAdapter(
                    data,
                    this@AvailableFragment,
                    isChecked = false,
                    isAnyUnchecked = false,
                    isSelectAll = false,
                    unSelectAllListener = this@AvailableFragment,
                    selectionEnabled = selectionEnabled,
                    singleListener = this@AvailableFragment,
                    unsingleListener = this@AvailableFragment
                )
            layoutManager = recyclerManager

        }


        binding.checkboxSelectAll.setOnCheckedChangeListener { buttonView, isChecked ->
            isAllSelected = isChecked
            if (!isAllSelected) {
                lifecycleScope.launch(Dispatchers.Main)
                {
                    itemViewModel.removeAll()
                }
            }
            binding.itemRecycler.adapter =
                ItemAdapter(
                    data,
                    this@AvailableFragment,
                    false,
                    isAnyUnchecked = false,
                    isSelectAll = isChecked,
                    unSelectAllListener = this@AvailableFragment,
                    selectionEnabled = selectionEnabled,
                    singleListener = this@AvailableFragment,
                    unsingleListener = this@AvailableFragment
                )

        }
        binding.disableItem.setOnClickListener {
            if (isAllSelected) {
                Log.d("ISAALL", "YES")
                var print = ""
                for (Data in data) {
                    print += (Data.itemName) + ","
                }
                Toast.makeText(activity, print, Toast.LENGTH_SHORT).show()

            } else {
                Log.d("ISSINGLE", "YES")

                if (isFromSingle) {
                    Toast.makeText(activity, datafromAdapter.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun loadDummyData() {

        data = listOf<Items>(
            Items(
                "1",
                getString(R.string.card_text) + "1"
            ),
            Items(
                "2",
                getString(R.string.card_text) + "2"
            ),
            Items(
                "3",
                getString(R.string.card_text) + "3"
            ),
            Items(
                "4",
                getString(R.string.card_text) + "4"
            ),
            Items(
                "5",
                getString(R.string.card_text) + "5"
            )
        )

    }

    override fun onLongItemClicked(items: Items): Boolean {
        selectionEnabled = true
        binding.hiddenItems.visibility = View.VISIBLE
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
            lifecycleScope.launch(Dispatchers.Main)
            {
                itemViewModel.addDataFromSelected(data)
            }
        }else
            {
                isFromSingle = false
            }
        }




    override fun onUnSingleSelected(data: String) {
        lifecycleScope.launch(Dispatchers.Main)
        {
            itemViewModel.removeDataFromSelected(data)

            lifecycleScope.launch(Dispatchers.Main)
            {
                itemViewModel.getDataFromSelected().observe(requireActivity(), Observer {
                    datafromAdapter.clear()
                    datafromAdapter.addAll(it)

                })


            }
        }

    }


}