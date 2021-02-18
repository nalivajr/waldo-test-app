package com.touchlane.waldotest.ui.album.adapter

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.touchlane.waldotest.domain.model.PhotoInfo
import com.touchlane.waldotest.domain.model.ThumbnailSize
import com.touchlane.waldotest.ui.widget.RoundedSquareImageView

class ImageInfoHolder(
        private val squareImageView: RoundedSquareImageView
    ) : RecyclerView.ViewHolder(squareImageView) {

    fun bind(imageInfo: PhotoInfo, preferredSize: ThumbnailSize) {
        squareImageView.loading = true
        val thumbToLoad = imageInfo.urls.firstOrNull { it.size.ordinal >= preferredSize.ordinal }
                ?: imageInfo.urls.firstOrNull()
        Picasso.get()
            .load(thumbToLoad?.url)
            .into(squareImageView.innerImageView, object: Callback {
                override fun onSuccess() {
                    squareImageView.loading = false
                }

                override fun onError(e: Exception?) {
                    squareImageView.loading = false
                }
            })
    }
}