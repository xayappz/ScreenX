package com.xayappz.screenx.utils

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
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
import com.xayappz.screenx.adapters.ReviewImageAdapter
import com.xayappz.screenx.models.ImageReview
import com.xayappz.screenx.models.ReviewImage
import com.xayappz.screenx.viewmodels.DialogViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


class ReviewDialog : DialogFragment(), ClickReview, DeleteReview {

    private var desc: String = ""
    var userId: String = ""
    private var userName: String = ""
    private var ratingCount: Float = 0f
    private var totalimaegs: Int = 0

    private var update: Boolean = false
    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100
    private lateinit var reviewImageAdapter: ReviewImageAdapter
    private var imageList: ArrayList<ImageReview> = ArrayList<ImageReview>()
    private var imageListSave: ArrayList<Bitmap?>? = ArrayList<Bitmap?>()
    private lateinit var reviewViewModel: DialogViewModel
    private var mutableLiveDataReviewByUser: MutableLiveData<List<ReviewImage>> =
        MutableLiveData<List<ReviewImage>>()
    lateinit var addImagesRV: RecyclerView
    lateinit var imageBytes: ByteArray
    lateinit var deleteReview: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //userId = rand(1, 100).toString()
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
//        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO)
//        {
//            var x = reviewViewModel.getImagesbyId(tag)
//            withContext(Dispatchers.Main) {
//                reviewImageAdapter =
//                    ReviewImageAdapter("",0, this@ReviewDialog.requireActivity(),null!!)
//                addImagesRV.adapter = reviewImageAdapter
//            }
//        }
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

//                reviewViewModel.updateUserAnswer(
//                    tag!!,
//                    name?.text.toString(),
//                    review?.text.toString(),
//                    rating?.rating
//                )


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


                        reviewViewModel.saveUserReviewImage(
                            name?.text.toString(),
                            imageListSave!!
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(activity, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {

            addImagesRV.visibility = View.VISIBLE
            imageBytes = Base64.decode(data.extras?.get("data").toString(), 0)
            imageList.add(ImageReview(0, stringtoBitmap()))
            reviewImageAdapter =
                ReviewImageAdapter("", 0, this@ReviewDialog.requireActivity(), null!!)
            addImagesRV.adapter = reviewImageAdapter
            reviewImageAdapter.notifyDataSetChanged()

        }
    }

    fun stringtoBitmap(): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val yuvimage = YuvImage(imageBytes, ImageFormat.YUY2, 120, 30, null)
            val baos = ByteArrayOutputStream()
            yuvimage.compressToJpeg(Rect(0, 0, 20, 20), 100, baos)
            val jdata = baos.toByteArray()
            bitmap = BitmapFactory.decodeByteArray(jdata, 0, jdata.size)


        } catch (e: Exception) {
        }
        imageListSave?.add(bitmap)

        if (update) {
            userId = tag.toString()
        }
        Log.d("USERID", userId + "...");

        return bitmap
    }

    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (Math.random() * (end - start + 1)).toInt() + start
    }

    override fun onCellClickListener(data: String) {
        Log.d("saddasdadd", data.toString())

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            reviewViewModel.deleteUserReviewImage(data)

        }
    }

    override fun onCellClickListener(data: String, userId: String) {
        TODO("Not yet implemented")
    }


    override fun onCellClickDelete(data: String) {
        TODO("Not yet implemented")
    }


}