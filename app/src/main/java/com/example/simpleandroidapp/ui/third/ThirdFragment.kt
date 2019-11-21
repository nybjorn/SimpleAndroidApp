package com.example.simpleandroidapp.ui.third

import android.graphics.Bitmap
import android.os.Bundle
import androidx.renderscript.Allocation
import androidx.renderscript.RenderScript
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.simpleandroidapp.R
import com.example.simpleandroidapp.ScriptC_mono
import com.example.simpleandroidapp.repository.pojo.Beer
import kotlinx.android.synthetic.main.fragment_third.*

class ThirdFragment : Fragment() {

    lateinit var beer: Beer

    private var mBitmapIn: Bitmap? = null
    private var mBitmapOut: Bitmap? = null

    private lateinit var mRS: RenderScript
    private var mInAllocation: Allocation? = null
    private var mOutAllocation: Allocation? = null
    private var mScript: ScriptC_mono? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        arguments?.let {
            beer = ThirdFragmentArgs.fromBundle(it).OneBeer
        }
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Navigation.findNavController(view).currentDestination?.let {
            it.label = beer.name
        }
        beer.foodPairing?.let {
            one_beer_title.text = it.joinToString(separator = "\n") { food -> "* $food" }
        }

        if(beer.imageUrl.isNullOrEmpty()) {
            beer_image.visibility = View.GONE
        } else {
            Glide.with(view).load(beer.imageUrl).into(beer_image)

            mBitmapIn = beer_image.drawable.toBitmap(beer_image.width, beer_image.height)
            mBitmapOut = Bitmap.createBitmap(
                mBitmapIn!!.width, mBitmapIn!!.height,
                mBitmapIn!!.config
            )

            beer_image.setImageBitmap(mBitmapIn)

            beer_image.setImageBitmap(mBitmapOut)

            createScript()
        }
    }

    private fun createScript() {
        mRS = RenderScript.create(activity)
        mInAllocation = Allocation.createFromBitmap(
            mRS, mBitmapIn,
            Allocation.MipmapControl.MIPMAP_NONE,
            Allocation.USAGE_SCRIPT
        )
        mOutAllocation = Allocation.createFromBitmap(
            mRS, mBitmapOut,
            Allocation.MipmapControl.MIPMAP_NONE,
            Allocation.USAGE_SCRIPT
        )

        mScript = ScriptC_mono(mRS)
        mScript!!.forEach_root(mInAllocation, mOutAllocation)
        mOutAllocation!!.copyTo(mBitmapOut)
    }

}


