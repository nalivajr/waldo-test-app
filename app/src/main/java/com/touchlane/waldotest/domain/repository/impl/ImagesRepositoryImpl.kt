package com.touchlane.waldotest.domain.repository.impl

import com.touchlane.waldotest.api.WaldoAPI
import com.touchlane.waldotest.domain.exception.ApiException
import com.touchlane.waldotest.domain.model.PhotoInfo
import com.touchlane.waldotest.domain.repository.ImagesService

class ImagesRepositoryImpl(
    private val waldoAPI: WaldoAPI
) : ImagesService {

    override fun loadPage(albumId: String, offset: Int, limit: Int): List<PhotoInfo> {
        val apiResponse = waldoAPI.getImages(albumId, offset, limit)
        if (apiResponse.isSuccess) {
            return apiResponse.requireData()
        } else {
            throw ApiException(apiResponse.errors)
        }
    }
}