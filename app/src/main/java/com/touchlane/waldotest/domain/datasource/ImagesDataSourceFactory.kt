package com.touchlane.waldotest.domain.datasource

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.touchlane.waldotest.domain.model.PhotoInfo
import com.touchlane.waldotest.domain.repository.ImagesService
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ImagesDataSourceFactory(
    private val imagesService: ImagesService
) : DataSource.Factory<Int, PhotoInfo>() {

    private var albumId: String = ""

    private val _activeListData: MutableLiveData<PagedList<PhotoInfo>> = MutableLiveData()

    val activeListData: LiveData<PagedList<PhotoInfo>> = _activeListData
    val loadingStateData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingErrorData: MutableLiveData<Throwable> = MediatorLiveData()

    override fun create(): DataSource<Int, PhotoInfo> {

        val dataSource = ImagesDataSource(albumId, imagesService, createLoadCallback())

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()

        _activeListData.value?.dataSource?.invalidate()

        val pagedList = PagedList.Builder(dataSource, config)
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .setNotifyExecutor(buildMainExecutor())
            .build()

        _activeListData.postValue(pagedList)

        return dataSource
    }

    private fun createLoadCallback() = object : ImagesDataSource.LoadCallback {
        override fun onStartLoading() {
            loadingStateData.postValue(true)
        }

        override fun onCompleteLoading() {
            loadingStateData.postValue(false)
        }

        override fun onLoadingFailed(e: Throwable) {
            loadingErrorData.postValue(e)
        }
    }

    private fun buildMainExecutor() = object : Executor {
        private val handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable?) {
            handler.post { command?.run() }
        }
    }

    fun setupForAlbumId(albumId: String) {
        this.albumId = albumId
        create()
    }

    companion object {
        private const val PAGE_SIZE = 50
    }
}