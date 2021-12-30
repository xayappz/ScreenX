package com.xayappz.screenx.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.xayappz.screenx.R
import com.xayappz.screenx.viewmodels.DialogViewModel

class ReviewDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_review_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width, height)
        val name = dialog?.findViewById<EditText>(R.id.editTextTextPersonName)
        val review = dialog?.findViewById<EditText>(R.id.editTextTextPersonName2)
        val rating = dialog?.findViewById<RatingBar>(R.id.ratingBar2)
        val add = dialog?.findViewById<Button>(R.id.addReview)
        add?.setOnClickListener {
            dialog?.dismiss()
            val viewModel =
                ViewModelProvider(context as ViewModelStoreOwner)[DialogViewModel::class.java]
            viewModel.sendUserAnswer(name?.text.toString(),review?.text.toString(),rating?.rating)
        }

    }

}