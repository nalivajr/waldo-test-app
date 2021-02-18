package com.touchlane.waldotest.ui.album.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.touchlane.waldotest.databinding.ItemListImageBinding
import com.touchlane.waldotest.domain.model.PhotoInfo
import com.touchlane.waldotest.domain.model.ThumbnailSize
import com.touchlane.waldotest.ui.widget.RoundedSquareImageView

class SimpleImagesAdapter(
        private val preferredSize: ThumbnailSize
    ) : ListAdapter<PhotoInfo, ImageInfoHolder>(ImagesInfoDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageInfoHolder {
        val itemBinding = ItemListImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageInfoHolder(itemBinding.root as RoundedSquareImageView)
    }

    override fun onBindViewHolder(holder: ImageInfoHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, preferredSize)
        }
    }
}