package com.touchlane.waldotest.ui.album.adapter

import androidx.recyclerview.widget.DiffUtil
import com.touchlane.waldotest.domain.model.PhotoInfo

class ImagesInfoDiffer : DiffUtil.ItemCallback<PhotoInfo>() {
    override fun areItemsTheSame(oldItem: PhotoInfo, newItem: PhotoInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PhotoInfo, newItem: PhotoInfo): Boolean {
        return true //since PhotoInfo is data class with val props
    }
}