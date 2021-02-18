package com.touchlane.waldotest.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.touchlane.waldotest.R
import com.touchlane.waldotest.databinding.FragmentBaseBinding
import com.touchlane.waldotest.ui.model.UiResult

abstract class BaseFragment<T: ViewDataBinding> : Fragment() {

    private lateinit var rootBinding: FragmentBaseBinding
    protected lateinit var binding: T

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        rootBinding = FragmentBaseBinding.inflate(inflater, container, false)
        rootBinding.stub.viewStub?.apply {
            layoutResource = getContentLayoutId()
            val contentView = inflate()
            binding = DataBindingUtil.bind<T>(contentView)!!
        }
        return rootBinding.root
    }

    abstract fun getContentLayoutId(): Int

    protected fun showProgress(visible: Boolean) {
        rootBinding.progress.isGone = !visible
    }

    protected fun <D> dispatchResult(result: UiResult<D>, onSuccess: (D?) -> Unit) {
        result.onSuccess(onSuccess)
                .onError { message, _ ->
                    showErrorDialog(message ?: requireContext().getString(R.string.error_unexpected_try_again))
                }
    }

    protected fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show()
    }
}