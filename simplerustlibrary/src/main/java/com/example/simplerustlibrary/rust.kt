package com.example.simplerustlibrary

external fun hello(to: String): String

fun loadRustLib(): Boolean = try {
    System.loadLibrary("simplerust")
    true
} catch (e: Throwable) {
    false
}
