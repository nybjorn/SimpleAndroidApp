package com.example.simpleandroidapp.repository

// https://proandroiddev.com/android-architecture-starring-kotlin-coroutines-jetpack-mvvm-room-paging-retrofit-and-dagger-7749b2bae5f7
// https://github.com/Eli-Fox/LEGO-Catalog/issues/1
sealed class RepositoryResult<out T> {
    class Loading<T> : RepositoryResult<T>()
    data class Error<T>(val message: String) : RepositoryResult<T>()
    data class IntermediateError<T>(val message: String) : RepositoryResult<T>()
    data class Success<T>(val data: T) : RepositoryResult<T>()
}
