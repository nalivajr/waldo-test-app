package com.touchlane.waldotest.ui.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.touchlane.waldotest.R
import com.touchlane.waldotest.databinding.FragmentLoginBinding
import com.touchlane.waldotest.ui.album.AlbumFragment
import com.touchlane.waldotest.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModel()

    override fun getContentLayoutId(): Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResultData.observe(viewLifecycleOwner) {
            dispatchResult(it) {
                findNavController().navigate(R.id.action_login_to_album, AlbumFragment.buildParams(AlbumFragment.DEMO_ALBUM_ID))
            }
        }

        viewModel.progressData.observe(viewLifecycleOwner, ::showProgress)

        viewModel.loginInputError.observe(viewLifecycleOwner) {
            binding.loginLayout.error = it
        }

        viewModel.passwordInputError.observe(viewLifecycleOwner) {
            binding.passwordLayout.error = it
        }

        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            etLogin.addTextChangedListener {
                viewModel.resetLoginError()
            }

            etPassword.addTextChangedListener {
                viewModel.resetPasswordError()
            }

            loginButton.setOnClickListener {
                viewModel.login(etLogin.text.toString(), etPassword.text.toString())
            }

            loginButton.setOnLongClickListener {
                etPassword.setText("!!waldo123_")
                etLogin.setText("thanos+ios+engineer@waldophotos.com")
                return@setOnLongClickListener true
            }
        }
    }
}