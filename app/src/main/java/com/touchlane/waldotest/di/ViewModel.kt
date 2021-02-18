package com.touchlane.waldotest.di

import com.touchlane.waldotest.ui.album.AlbumViewModel
import com.touchlane.waldotest.ui.login.LoginViewModel
import com.touchlane.waldotest.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        SplashViewModel(get())
    }

    viewModel {
        LoginViewModel(get(), get(), get())
    }

    viewModel { params ->
        AlbumViewModel( params[0], get(), get())
    }
}