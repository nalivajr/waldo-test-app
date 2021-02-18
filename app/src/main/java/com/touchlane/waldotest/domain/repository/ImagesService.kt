package com.touchlane.waldotest.domain.repository

import com.touchlane.waldotest.domain.model.PhotoInfo

interface ImagesService {

    fun loadPage(albumId: String, offset: Int, limit: Int): List<PhotoInfo>
}