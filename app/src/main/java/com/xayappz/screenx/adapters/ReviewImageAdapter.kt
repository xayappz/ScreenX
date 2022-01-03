package com.xayappz.screenx.adapters

import android.graphics.*
import android.os.Build
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.xayappz.screenx.R
import com.xayappz.screenx.models.ImageReview
import com.xayappz.screenx.models.Images
import com.xayappz.screenx.utils.ClickReview
import java.io.ByteArrayOutputStream


internal class ReviewImageAdapter(
    private var reviewImageList: ArrayList<ImageReview>?,
    private var images: ArrayList<Images>?,
    var reviewClick: ClickReview
) :
    RecyclerView.Adapter<ReviewImageAdapter.MyViewHolder>() {
    lateinit var imageBytes: ByteArray

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.imageReviewIV)
        var remove_rev_Img: ImageView = view.findViewById(R.id.imageView2)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Log.d("BITMAP", reviewImageList.get(position).imageBmp.toString() + "...")

        imageBytes = Base64.decode(images?.get(position)?.imageBmp.toString(), 0)

        holder.image.setImageBitmap(stringtoBitmap(imageBytes))
        holder.remove_rev_Img.setOnClickListener {

            reviewClick.onCellClickListener(
                images?.get(position)?.imageBmp.toString()

            )
            images?.removeAt(position)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        if(images==null)
        {
            return reviewImageList!!.size

        }else
        {
            return images!!.size

        }
    }

    fun stringtoBitmap(imageBytes: ByteArray): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val yuvimage = YuvImage(imageBytes, ImageFormat.YUY2, 120, 30, null)
            val baos = ByteArrayOutputStream()
            yuvimage.compressToJpeg(Rect(0, 0, 20, 20), 100, baos)
            val jdata = baos.toByteArray()
            bitmap = BitmapFactory.decodeByteArray(jdata, 0, jdata.size)
        } catch (e: Exception) {
        }
        //imageListSave?.add(bitmap)

        return bitmap
    }


}