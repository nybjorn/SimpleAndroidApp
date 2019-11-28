package com.example.simplerustlibrary

external fun hello(to: String): String
external fun helloDirect(to: String): String

fun loadRustLib() {
    System.loadLibrary("libsimplerust")
}