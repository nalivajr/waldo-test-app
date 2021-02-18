package com.touchlane.waldotest.domain.model

enum class ThumbnailSize(private val sizeCode: String) {
    TINY("small"),
    SMALL("small2x"),
    MEDIUM("medium"),
    LARGE("medium2x"),
    X_LARGE("large"),
    XX_LARGE("large1x"),
    XXX_LARGE("large2x");

    companion object {
        fun fromSizeCode(sizeCode: String): ThumbnailSize {
            return values().find { it.sizeCode == sizeCode } ?: TINY
        }
    }
}