package com.example.simpleandroidapp.ui.main

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.simpleandroidapp.R
import com.example.simpleandroidapp.messaging.EventMessage
import com.example.simplerustlibrary.loadRustLib
import com.example.simplerustlibrary.mandelbrotKotlin2
import com.example.simplerustlibrary.mandelrust
import kotlinx.android.synthetic.main.fragment_first.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FirstFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventMessage) {
        message.text = event.beerMessage
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var message: String
        if (loadRustLib()) {
            val startTime = System.currentTimeMillis()
            val brot = 200
            val mandelArray = mandelbrotKotlin2(brot, brot)
            val mandelBrot = Bitmap.createBitmap(mandelArray, 0, brot, brot, brot, Bitmap.Config.ARGB_8888)
            val executionTime = System.currentTimeMillis() - startTime
            kotlin_mandel.setImageBitmap(mandelBrot)
            val kotlinTime = executionTime.toString()
            val startTimeRust = System.currentTimeMillis()
            val mandelArrayRust = mandelrust(brot, brot)
            val mandelBrotRust = Bitmap.createBitmap(mandelArrayRust, 0, brot, brot, brot, Bitmap.Config.ARGB_8888)
            val executionTimeRust = System.currentTimeMillis() - startTimeRust

            message = "Kotlintime: {$kotlinTime} RustTime: {$executionTimeRust}"
            rust_mandel.setImageBitmap(mandelBrotRust)
        } else {
            message = "Unable to load required library"
        }

        rust_message.text = message
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
}
