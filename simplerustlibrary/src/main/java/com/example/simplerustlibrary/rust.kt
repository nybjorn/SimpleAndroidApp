package com.example.simplerustlibrary

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.sqrt

external fun hello(to: String): String
external fun mandelrust(width: Int, height: Int): String
/*
class mandel {
companion object {
    @JvmStatic
}
}
 */
fun loadRustLib(): Boolean = try {
    System.loadLibrary("simplerust")
    true
} catch (e: Throwable) {
    false
}

fun map(n: Float, start1: Float, end1: Float, start2: Float, end2: Float) =
    ((n - start1) / (end1 - start1)) * (end2 - start2) + start2

fun mandelbrotKotlin(width: Int, height: Int): Bitmap {
    var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    var N = 0
    for (x in 0 until width - 1) {
        for (y in 0 until height - 1) {
            var a = map(x.toFloat(), 0F, width.toFloat(), -2F, 2F)
            var b = map(y.toFloat(), 0F, height.toFloat(), -2F, 2F)
            val A = a
            val B = b
            val maxM = 1000
            for (n in 0..maxM) {
                val ab = a * a - b * b
                val bb = 2 * a * b
                a = ab + A
                b = bb + B
                N = n
                if (a * a + b * b > 4) break
            }
            var brightness = if (N==maxM) 0 else  map(sqrt(N.toFloat() / maxM.toFloat()), 0F, 1F, 0F, 255F).toInt()

            val color = Color.argb(255, brightness, brightness*2, brightness)

            bitmap.setPixel(x, y, color)
            //  pixels[pix + 1] = brightness
          //  pixels[pix + 2] = brightness * 2
          //  pixels[pix + 3] = 255
        }
    }
    return bitmap
}

fun mandelbrotKotlin2(width: Int, height: Int): IntArray{
    val pixels = IntArray(width * height) { Color.GREEN }
    var largeN = 0
    for (x in 0 until width - 1) {
        for (y in 0 until height - 1) {
            var a = map(x.toFloat(), 0F, width.toFloat(), -2F, 2F)
            var b = map(y.toFloat(), 0F, height.toFloat(), -2F, 2F)
            val A = a
            val B = b
            val maxM = 1000
            for (n in 0..maxM) {
                val ab = a * a - b * b
                val bb = 2 * a * b
                a = ab + A
                b = bb + B
                largeN = n
                if (a * a + b * b > 4) break
            }
            val pix = (x + y * width) //* 4
            val brightness = if (largeN == maxM) 0  else map(sqrt(largeN.toFloat() / maxM.toFloat()), 0F, 1F, 0F, 255F).toInt()

            val color = Color.argb(255, brightness, brightness*2, brightness)

           // bitmap.setPixel(x, y, color)
            pixels[pix] = color
            //  pixels[pix + 2] = brightness * 2
            //  pixels[pix + 3] = 255
        }
    }
    return pixels
}
