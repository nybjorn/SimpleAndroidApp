package com.example.simplerustlibrary

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RustLibraryExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.simplerustlibrary.test", appContext.packageName)
    }

    @Test
    fun loadLibrary() {
        assertTrue(loadRustLib())
    }

    @Test
    fun helloFromRust() {
        loadLibrary()
        assertEquals("Hello from Rust: Johan", hello("Johan"))
    }


    @Test
    fun mandelKotlin() {
        val mandelBrot = mandelbrotKotlin(100, 800)
        println("mandelBrot(800, 800) = $mandelBrot")
    }

    @Test
    fun mandelRust() {
        loadLibrary()
        val mandelBrot = mandelrust(100, 800)
        println("mandelBrot(800, 800) = $mandelBrot")
    }


}
