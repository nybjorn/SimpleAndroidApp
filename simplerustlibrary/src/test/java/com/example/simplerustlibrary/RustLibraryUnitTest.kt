package com.example.simplerustlibrary

import org.junit.Assert.assertFalse
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RustLibraryUnitTest {
    @Test
    fun loadLibrary() {
        assertFalse(loadRustLib())
    }

    @Test
    fun testMandelRust() {
        val currentDir = System.getProperty("user.dir")
        System.load(currentDir + "/build/rustJniLibs/desktop/darwin/libsimplerust.dylib")
        println("hello(\"hello\") = ${hello("hello")}")
        mandelrust(800, 800)
    }




}
