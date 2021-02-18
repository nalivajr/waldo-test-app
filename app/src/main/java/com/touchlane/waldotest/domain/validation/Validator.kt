package com.touchlane.waldotest.domain.validation

interface Validator {

    fun validate(rule: () -> Boolean,
                 onValid: () -> Unit = {},
                 onInvalid: (String, Throwable) -> Unit = { _, _ -> }
    ): Boolean
}