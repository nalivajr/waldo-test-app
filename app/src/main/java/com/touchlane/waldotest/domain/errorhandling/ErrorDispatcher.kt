package com.touchlane.waldotest.domain.errorhandling

interface ErrorDispatcher {

    fun dispatchError(error: Throwable?): String
}