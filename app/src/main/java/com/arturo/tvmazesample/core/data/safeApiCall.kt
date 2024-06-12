package com.arturo.tvmazesample.core.data

import com.arturo.tvmazesample.core.domain.NETWORK_ERROR_TIMEOUT
import com.arturo.tvmazesample.core.domain.ResultWrapper
import com.arturo.tvmazesample.core.domain.UNKNOWN_ERROR
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

// class used for catching the errors from the request and making them able to be handled
// Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    timeout: Long = 60000,
    apiCall: suspend () -> T,
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            withTimeout(timeout) {
                ResultWrapper.Success(apiCall.invoke())
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408 // timeout error code
                    ResultWrapper.GenericError(code, NETWORK_ERROR_TIMEOUT)
                }

                is IOException -> {
                    val error = throwable.message
                    ResultWrapper.NetworkError(error)
                }

                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }

                else -> {
                    val message = "${throwable.cause} :  ${throwable.message}"
                    ResultWrapper.GenericError(null, message)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()
    } catch (exception: Exception) {
        UNKNOWN_ERROR
    }
}