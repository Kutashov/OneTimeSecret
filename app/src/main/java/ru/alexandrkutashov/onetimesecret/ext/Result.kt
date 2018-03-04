package ru.alexandrkutashov.onetimesecret.ext

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

sealed class Result<out T : Any> {

    class Success<out T : Any>(val data: T) : Result<T>()

    class Error(val exception: Exception) : Result<Nothing>()
}

inline fun <reified T : Any> Result<T>.data(failure: Result.Error.() -> Unit): T? =
        when (this) {
            is Result.Success -> data
            is Result.Error -> {
                failure(this)
                null
            }
        }
