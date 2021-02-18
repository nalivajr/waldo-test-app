package com.touchlane.waldotest.domain.datasource

import androidx.paging.PositionalDataSource
import com.touchlane.waldotest.domain.model.PhotoInfo
import com.touchlane.waldotest.domain.repository.ImagesService

class ImagesDataSource(
    private val albumId: String,
    private val imagesService: ImagesService,
    private val loadCallback: LoadCallback
) : PositionalDataSource<PhotoInfo>() {

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<PhotoInfo>
    ) {
        doLoad(params.requestedStartPosition, params.requestedLoadSize) {
            callback.onResult(it, params.requestedStartPosition)
        }
    }

    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<PhotoInfo>
    ) {
        doLoad(params.startPosition, params.loadSize, callback::onResult)
    }

    private fun doLoad(offset: Int, limit: Int, onLoaded: (List<PhotoInfo>) -> Unit) {
        try {
            loadCallback.onStartLoading()
            val data = imagesService.loadPage(albumId, offset, limit)
            onLoaded(data)
        } catch (e: Throwable) {
            loadCallback.onLoadingFailed(e)
        } finally {
            loadCallback.onCompleteLoading()
        }
    }

    interface LoadCallback {

        fun onStartLoading()

        fun onCompleteLoading()

        fun onLoadingFailed(e: Throwable)
    }
}