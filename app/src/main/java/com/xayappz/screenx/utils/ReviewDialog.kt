package com.xayappz.screenx.utils

import android.app.ActionBar
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.models.ReviewImage
import com.xayappz.screenx.viewmodels.DialogViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class ReviewDialog : DialogFragment(), ClickReview, DeleteReview {

    private var desc: String = ""
    private var userName: String = ""
    private var ratingCount: Float = 0f
    private var totalimaegs: Int = 0
    private var update: Boolean = false
    private var imageListSave: ArrayList<Bitmap?>? = ArrayList<Bitmap?>()
    private lateinit var reviewViewModel: DialogViewModel
    private var mutableLiveDataReviewByUser: MutableLiveData<List<ReviewImage>> =
        MutableLiveData<List<ReviewImage>>()
    lateinit var addImagesRV: RecyclerView
    lateinit var deleteReview: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_review_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(true)
        val window: Window? = dialog?.getWindow()
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        val name = dialog?.findViewById<EditText>(R.id.editTextTextPersonName)
        val review = dialog?.findViewById<EditText>(R.id.editTextTextPersonName2)
        var rating = dialog?.findViewById<RatingBar>(R.id.ratingBar2)

        var count = dialog?.findViewById<EditText>(R.id.enterImagecount)


        addImagesRV = dialog?.findViewById(R.id.addImagesRV)!!
        deleteReview = dialog?.findViewById(R.id.imageView3)!!
        val layoutManager = LinearLayoutManager(activity)
        reviewViewModel =
            ViewModelProvider(context as ViewModelStoreOwner)[DialogViewModel::class.java]

        addImagesRV.layoutManager = layoutManager
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val add = dialog?.findViewById<Button>(R.id.addReview)

        if (!tag.isNullOrEmpty()) {
            update = true
            deleteReview.visibility = View.VISIBLE
            add?.text = "Update"

            val reviewViewModel = ViewModelProvider(
                this,
                DialogFactory(application = activity?.application)
            )[DialogViewModel::class.java]


            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                mutableLiveDataReviewByUser = reviewViewModel.getReviewById(tag)

                withContext(Dispatchers.Main) {

                    ratingCount = mutableLiveDataReviewByUser.value?.get(0)?.rating!!
                    desc = mutableLiveDataReviewByUser.value?.get(0)?.description!!
                    userName = mutableLiveDataReviewByUser.value?.get(0)?.name!!
                    totalimaegs = mutableLiveDataReviewByUser.value?.get(0)?.image!!

                    name?.setText(userName)
                    review?.setText(desc)
                    rating?.rating = ratingCount
                    count?.setText(totalimaegs.toString())

                }

            }


        }

        deleteReview.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                reviewViewModel.deleteReview(tag)
                dialog?.dismiss()
            }
        }

        add?.setOnClickListener {
            if (name?.text?.length == 0 || review?.text?.length == 0) {


                Toast.makeText(activity, "Please write details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            if (update) {

                reviewViewModel.updateUserAnswer(
                    tag.toString(),
                    name?.text.toString(),
                    review?.text.toString(),
                    rating?.rating,
                    count?.text.toString()
                )

            } else {

                var isExists = false
                lifecycleScope.launch(Dispatchers.IO)
                {

                    isExists = reviewViewModel.isUserExists(name?.text.toString())
                    var x: String? = ""
                    x = count?.text?.toString()
                    if (!isExists) {
                        reviewViewModel.saveUserAnswer(
                            name?.text.toString(),
                            review?.text.toString(),
                            rating?.rating,
                            x
                        )




                    } else {
                        withContext(Dispatchers.Main)
                        {
                            Toast.makeText(activity, "User Already Exists", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                }


            }
            dialog?.dismiss()

        }


    }


    override fun onCellClickListener(data: String) {


    }

    override fun onCellClickListener(data: String, userId: String) {
        TODO("Not yet implemented")
    }


    override fun onCellClickDelete(data: String) {
        TODO("Not yet implemented")
    }


}