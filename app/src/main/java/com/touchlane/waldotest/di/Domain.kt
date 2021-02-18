package com.touchlane.waldotest.di

import com.touchlane.waldotest.domain.datasource.ImagesDataSourceFactory
import com.touchlane.waldotest.domain.errorhandling.ErrorDispatcher
import com.touchlane.waldotest.domain.errorhandling.ErrorDispatcherImpl
import com.touchlane.waldotest.domain.repository.AuthenticationService
import com.touchlane.waldotest.domain.repository.impl.ImagesRepositoryImpl
import com.touchlane.waldotest.domain.repository.ImagesService
import com.touchlane.waldotest.domain.repository.impl.AuthenticationServiceImpl
import com.touchlane.waldotest.domain.validation.SimpleValidator
import com.touchlane.waldotest.domain.validation.Validator
import org.koin.dsl.module

val domainModule = module {

    single<ImagesService> { ImagesRepositoryImpl(get()) }

    single<ErrorDispatcher> { ErrorDispatcherImpl(get()) }

    single<Validator> { SimpleValidator(get()) }

    single<AuthenticationService> { AuthenticationServiceImpl(get(), get()) }

    factory { ImagesDataSourceFactory(get()) }
}