package com.touchlane.waldotest.ui.model

class UiResult<T>(
    val data: T?,
    val message: String?,
    val error: Throwable?
) {
    private val isSuccess: Boolean
        get() = error == null && message == null

    fun onSuccess(action: (T?) -> Unit): UiResult<T> {
        if (isSuccess) {
            action.invoke(data)
        }
        return this
    }

    fun onError(action: (String?, Throwable?) -> Unit): UiResult<T> {
        if (!isSuccess) {
            action.invoke(message, error)
        }
        return this
    }

    companion object {

        fun <T> success(data: T?): UiResult<T> {
            return UiResult(data, null, null)
        }

        fun <T> error(message: String, error: Throwable? = null): UiResult<T> {
            return UiResult(null, message, error)
        }
    }
}