package com.example.simpleandroidapp.repository

//https://proandroiddev.com/android-architecture-starring-kotlin-coroutines-jetpack-mvvm-room-paging-retrofit-and-dagger-7749b2bae5f7
//https://github.com/Eli-Fox/LEGO-Catalog/issues/1
sealed class RepositoryResult<out T> {
    class loading<T> : RepositoryResult<T>()
    data class error<T>(val message: String) : RepositoryResult<T>()
    data class intermediateError<T>(val message: String): RepositoryResult<T>()
    data class success<T>(val data: T) : RepositoryResult<T>()
}