package com.touchlane.waldotest.ui.album

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.touchlane.waldotest.R
import com.touchlane.waldotest.databinding.FragmentAlbumBinding
import com.touchlane.waldotest.domain.model.ThumbnailSize
import com.touchlane.waldotest.ui.album.adapter.ItemDecoration
import com.touchlane.waldotest.ui.album.adapter.SimpleImagesAdapter
import com.touchlane.waldotest.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.parametersOf

class AlbumFragment : BaseFragment<FragmentAlbumBinding>() {

    private val viewModel: AlbumViewModel by viewModel(parameters = {
        parametersOf(
            requireArguments().getString(ARG_ALBUM_ID)
        )
    })

    private lateinit var adapter: SimpleImagesAdapter

    override fun getContentLayoutId(): Int = R.layout.fragment_album

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SimpleImagesAdapter(ThumbnailSize.MEDIUM)
        binding.albumImages.adapter = adapter
        binding.albumImages.layoutManager = GridLayoutManager(requireActivity(), calculateColumns(), GridLayoutManager.VERTICAL, false)
        binding.albumImages.addItemDecoration(ItemDecoration(requireContext()))

        binding.swipeLayout.setOnRefreshListener { viewModel.reload() }

        viewModel.imagesListData.observe(viewLifecycleOwner, adapter::submitList)

        viewModel.loadingData.observe(viewLifecycleOwner) {
            binding.swipeLayout.isRefreshing = it
        }

        viewModel.loadingErrorData.observe(viewLifecycleOwner, ::showErrorDialog)

        if (savedInstanceState == null) {
            viewModel.reload()
        }
    }

    private fun calculateColumns(): Int {
        val orientation = resources.configuration.orientation
        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            SPAN_COUNT_LAND
        } else {
            SPAN_COUNT_PORT
        }
    }

    companion object {
        const val DEMO_ALBUM_ID = "exHbvthJfbuFwiusSJuVAm"

        private const val SPAN_COUNT_LAND = 10
        private const val SPAN_COUNT_PORT = 5
        private const val ARG_ALBUM_ID = "arg-album-id"

        fun buildParams(albumId: String): Bundle {
            return Bundle().apply {
                putString(ARG_ALBUM_ID, albumId)
            }
        }
    }
}