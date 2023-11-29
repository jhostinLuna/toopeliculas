package com.jhostinlh.topeliculas.core.platform

import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.functional.Either
import retrofit2.Call

fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
    return try {
        val response = call.execute()
        when (response.isSuccessful) {
            true -> Either.Right(transform((response.body() ?: default)))
            false -> Either.Left(Failure.ServerError())
        }
    } catch (exception: Throwable) {
        Either.Left(Failure.ServerError())
    }
}