package com.touchlane.waldotest.domain.validation

import com.touchlane.waldotest.domain.errorhandling.ErrorDispatcher
import com.touchlane.waldotest.domain.exception.ValidationException

class SimpleValidator(
    private val errorDispatcher: ErrorDispatcher
) : Validator {

    override fun validate(rule: () -> Boolean,
                 onValid: () -> Unit,
                 onInvalid: (String, Throwable) -> Unit
    ): Boolean {
        return runCatching {
            val res = rule.invoke()
            if (!res) {
                throw ValidationException()
            }
        }.fold(
            onSuccess = {
                onValid()
                true
            },
            onFailure = {
                onInvalid(errorDispatcher.dispatchError(it), it)
               false
            }
        )
    }
}