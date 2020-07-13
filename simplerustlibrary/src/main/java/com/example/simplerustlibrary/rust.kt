package com.example.simplerustlibrary

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.sqrt

external fun hello(to: String): String
external fun mandelrust(width: Int, height: Int): IntArray
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
} catch (e: LinkageError) {
    false
}

fun map(n: Float, start1: Float, end1: Float, start2: Float, end2: Float) =
    ((n - start1) / (end1 - start1)) * (end2 - start2) + start2

private const val MAX_ITERATIONS = 1000
private const val MAX_COLOR = 255
private const val POSITIVE_MANDEL_SPACE = 2F
private const val NEGATIVE_MANDEL_SPACE = -POSITIVE_MANDEL_SPACE
private const val NO_BETTER_SOLUTION = 4

@SuppressWarnings("NestedBlockDepth")
fun mandelbrotKotlin(width: Int, height: Int): Bitmap {
    var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    var largeN = 0
    for (x in 0 until width - 1) {
        for (y in 0 until height - 1) {
            var a = map(x.toFloat(), 0F, width.toFloat(), NEGATIVE_MANDEL_SPACE, POSITIVE_MANDEL_SPACE)
            var b = map(y.toFloat(), 0F, height.toFloat(), NEGATIVE_MANDEL_SPACE, POSITIVE_MANDEL_SPACE)
            val largeA = a
            val largeB = b
            val maxIterations = MAX_ITERATIONS
            for (n in 0..maxIterations) {
                val ab = a * a - b * b
                val bb = 2 * a * b
                a = ab + largeA
                b = bb + largeB
                largeN = n
                if (a * a + b * b > NO_BETTER_SOLUTION) break
            }
            var brightness =
                if (largeN == maxIterations) {
                  0
                } else {
                    map(
                        sqrt(largeN.toFloat() / maxIterations.toFloat()),
                        0F,
                        1F,
                        0F,
                        MAX_COLOR.toFloat()
                    ).toInt()
                }
            val color = Color.argb(MAX_COLOR, brightness, brightness * 2, brightness)

            bitmap.setPixel(x, y, color)
        }
    }
    return bitmap
}
@SuppressWarnings("NestedBlockDepth")
fun mandelbrotKotlin2(width: Int, height: Int): IntArray {
    val pixels = IntArray(width * height) { Color.GREEN }
    var largeN = 0
    for (x in 0 until width - 1) {
        for (y in 0 until height - 1) {
            var a = map(x.toFloat(), 0F, width.toFloat(), NEGATIVE_MANDEL_SPACE, POSITIVE_MANDEL_SPACE)
            var b = map(y.toFloat(), 0F, height.toFloat(), NEGATIVE_MANDEL_SPACE, POSITIVE_MANDEL_SPACE)
            val largeA = a
            val largeB = b
            val maxIterations = MAX_ITERATIONS
            for (n in 0..maxIterations) {
                val ab = a * a - b * b
                val bb = 2 * a * b
                a = ab + largeA
                b = bb + largeB
                largeN = n
                if (a * a + b * b > NO_BETTER_SOLUTION) break
            }
            val pix = (x + y * width)
            val brightness = if (largeN == maxIterations) { 0 } else {
                map(
                    sqrt(largeN.toFloat() / maxIterations.toFloat()),
                    0F,
                    1F,
                    0F,
                    MAX_COLOR.toFloat()
                ).toInt()
            }
            val color = Color.argb(MAX_COLOR, brightness, brightness * 2, brightness)

            pixels[pix] = color
        }
    }
    return pixels
}
