package com.example.simpleandroidapp.ui.main

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpleandroidapp.databinding.FragmentFirstBinding
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

    // java.lang.AssertionError: Unbound symbols not allowed
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventMessage) {
        binding.message.text = event.beerMessage
    }
    @SuppressWarnings("MagicNumber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var message: String
        if (loadRustLib()) {
            val startTime = System.currentTimeMillis()
            val brot = 200
            val mandelArray = mandelbrotKotlin2(brot, brot)
            val mandelBrot =
                Bitmap.createBitmap(mandelArray, 0, brot, brot, brot, Bitmap.Config.ARGB_8888)
            val executionTime = System.currentTimeMillis() - startTime
            binding.kotlinMandel.setImageBitmap(mandelBrot)
            val kotlinTime = executionTime.toString()
            val startTimeRust = System.currentTimeMillis()
            val mandelArrayRust = mandelrust(brot, brot)
            val mandelBrotRust =
                Bitmap.createBitmap(mandelArrayRust, 0, brot, brot, brot, Bitmap.Config.ARGB_8888)
            val executionTimeRust = System.currentTimeMillis() - startTimeRust

            message = "Kotlintime: {$kotlinTime} RustTime: {$executionTimeRust}"
            binding.rustMandel.setImageBitmap(mandelBrotRust)
        } else {
            message = "Unable to load required library"
        }

        binding.rustMessage.text = message
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
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
