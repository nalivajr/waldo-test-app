package com.touchlane.waldotest.ui.album

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.touchlane.waldotest.domain.datasource.ImagesDataSourceFactory
import com.touchlane.waldotest.domain.errorhandling.ErrorDispatcher
import com.touchlane.waldotest.domain.model.PhotoInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumViewModel(
    private val albumId: String,
    private val imagesDataSourceFactory: ImagesDataSourceFactory,
    errorDispatcher: ErrorDispatcher
) : ViewModel() {

    val loadingData: MutableLiveData<Boolean> = imagesDataSourceFactory.loadingStateData
    val loadingErrorData: LiveData<String> = MediatorLiveData<String>().apply {
        addSource(imagesDataSourceFactory.loadingErrorData) {
            postValue(errorDispatcher.dispatchError(it))
        }
    }

    val imagesListData: LiveData<PagedList<PhotoInfo>> = imagesDataSourceFactory.activeListData

    fun reload() {
        viewModelScope.launch(Dispatchers.IO) {
            imagesDataSourceFactory.setupForAlbumId(albumId)
        }
    }
}
