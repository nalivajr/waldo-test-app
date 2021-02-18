package com.touchlane.waldotest.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.touchlane.waldotest.R

class RoundedSquareImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleAttrId: Int = 0,
    styleId: Int = 0
) : ConstraintLayout(context, attributeSet, styleAttrId, styleId) {

    val innerImageView: ImageView
    private val progressView: ProgressBar
    private val rootCardView: CardView

    var loading: Boolean = false
        set(value) {
            field = value
            innerImageView.visibility = if (value) INVISIBLE else VISIBLE
            progressView.visibility = if (value) VISIBLE else GONE
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_rounded_square_image, this, true)
        rootCardView = findViewById(R.id.container)
        innerImageView = findViewById(R.id.image)
        progressView = findViewById(R.id.image_progress)
    }
}