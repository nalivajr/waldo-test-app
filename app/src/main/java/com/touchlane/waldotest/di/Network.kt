package com.touchlane.waldotest.di

import com.apollographql.apollo.ApolloClient
import com.squareup.picasso.BuildConfig
import com.touchlane.waldotest.api.WaldoAPI
import com.touchlane.waldotest.api.WaldoApiImpl
import com.touchlane.waldotest.domain.session.SessionManager
import com.touchlane.waldotest.domain.session.SessionManagerImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

private const val TIMEOUT_SECONDS = 60L
private const val CONNECT_TIMEOUT_SECONDS = 15L

private const val AUTH_HEADER = "Authorization"

const val BASE_API_URL = WaldoAPI.BASE_URL

val networkModule = module {

    single { okHttpClient(get()) }

    single { sessionManager() }

    factory { apolloClient(BASE_API_URL, get()) }

    single { waldoApi(get()) }
}

private fun sessionManager(): SessionManager {
    return SessionManagerImpl()
}

private fun okHttpClient(sessionManager: SessionManager): OkHttpClient {
    return okHttpClientBuilder(sessionManager).build()
}

private fun okHttpClientBuilder(sessionManager: SessionManager): OkHttpClient.Builder {
    val okHttpClientBuilder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        })
    }

    okHttpClientBuilder.readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    okHttpClientBuilder.writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    okHttpClientBuilder.connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
    okHttpClientBuilder.addInterceptor { chain ->
        val sessionToken = sessionManager.getSessionToken()
        val request = sessionToken?.let {
            chain.request()
                .newBuilder()
                .addHeader(AUTH_HEADER, "Bearer $sessionToken")
                .build()
        } ?: chain.request()
        return@addInterceptor chain.proceed(request)
    }
    return okHttpClientBuilder
}

private fun apolloClient(baseUrl: String, okHttpClient: OkHttpClient): ApolloClient {
    return ApolloClient.builder()
        .serverUrl(baseUrl)
        .okHttpClient(okHttpClient)
        .build()
}

fun waldoApi(apolloClient: ApolloClient): WaldoAPI {
    return WaldoApiImpl(apolloClient)
}