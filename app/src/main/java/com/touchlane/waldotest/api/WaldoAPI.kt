package com.touchlane.waldotest.api

import com.touchlane.waldotest.domain.model.PhotoInfo
import com.touchlane.waldotest.domain.model.ApiResponse

interface WaldoAPI {

    fun login(username: String, password: String): ApiResponse<String>

    fun getImages(albumId: String, offset: Int, limit: Int): ApiResponse<List<PhotoInfo>>

    companion object {
        const val BASE_URL = "https://core-graphql.dev.waldo.photos/gql"
    }
}