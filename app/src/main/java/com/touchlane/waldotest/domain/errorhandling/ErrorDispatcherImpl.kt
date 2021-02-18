package com.touchlane.waldotest.domain.errorhandling

import android.content.Context
import com.apollographql.apollo.exception.ApolloNetworkException
import com.touchlane.waldotest.R
import com.touchlane.waldotest.domain.exception.*
import java.io.IOException
import java.net.UnknownHostException

class ErrorDispatcherImpl(val context: Context): ErrorDispatcher {

    override fun dispatchError(error: Throwable?): String {
        return when (error) {
            is ApolloNetworkException, is UnknownHostException, is IOException -> context.getString(R.string.error_no_network)
            is ApiException -> error.messages.firstOrNull() ?: context.getString(R.string.error_unexpected_try_again)
            is ValidationException -> context.getString(R.string.error_invalid_input)
            is EmptyUsernameException -> context.getString(R.string.error_username_required)
            is InvalidUsernameException -> context.getString(R.string.error_username_invalid)
            is EmptyPasswordException -> context.getString(R.string.error_password_required)
            else -> context.getString(R.string.error_unexpected_try_again)
        }
    }
}