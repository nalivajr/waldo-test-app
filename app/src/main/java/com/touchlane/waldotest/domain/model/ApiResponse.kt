package com.touchlane.waldotest.domain.model

data class ApiResponse<T>(
    val data: T?,
    val errors: List<String>
) {
    val isSuccess: Boolean
        get() = errors.isEmpty()

    fun requireData() = data!!

    companion object {

        fun <T> error(errors: List<String>) = ApiResponse<T>(null, errors)

        fun <T> success(data: T?) = ApiResponse(data, emptyList())
    }
}