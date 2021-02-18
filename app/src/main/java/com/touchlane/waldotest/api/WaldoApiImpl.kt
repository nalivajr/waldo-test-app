package com.touchlane.waldotest.api

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.touchlane.waldotest.api.data.DoLoginMutation
import com.touchlane.waldotest.api.data.GetAlbumPhotosQuery
import com.touchlane.waldotest.domain.model.ApiResponse
import com.touchlane.waldotest.domain.model.PhotoInfo
import com.touchlane.waldotest.domain.model.ThumbnailInfo
import com.touchlane.waldotest.domain.model.ThumbnailSize
import kotlinx.coroutines.runBlocking

class WaldoApiImpl(private val apolloClient: ApolloClient) : WaldoAPI {

    override fun login(username: String, password: String): ApiResponse<String> {
        return apolloClient.mutate(DoLoginMutation(username, password))
            .toApiResponse {
                this?.accountLogin?.auth_token ?: ""
            }
    }

    override fun getImages(albumId: String, offset: Int, limit: Int): ApiResponse<List<PhotoInfo>> {
        return  apolloClient.query(GetAlbumPhotosQuery(albumId, offset, limit))
            .toApiResponse {
                this?.album?.photos?.records?.map { record ->
                    val thumbs = record?.thumbnail_urls?.filterNotNull()
                            ?.map {
                                ThumbnailInfo(
                                        it.size_code?.let(ThumbnailSize::fromSizeCode) ?: ThumbnailSize.TINY,
                                        it.width ?: 0,
                                        it.height ?: 0,
                                        it.url ?: ""
                                )
                    } ?: emptyList()
                    PhotoInfo(
                        record?.id.toString(),
                        thumbs
                    )
                } ?: emptyList()
            }
    }

    private fun <T, R> ApolloCall<T>.toApiResponse(mapper: T?.() -> R): ApiResponse<R> {
        val result: ApiResponse<R>
        runBlocking {
            val apolloResponse = await()
            val errorsList = apolloResponse.errors?.map { it.message } ?: emptyList()
            if (errorsList.isNotEmpty()) {
                result = ApiResponse.error(errorsList)
            } else {
                result = ApiResponse.success(mapper(apolloResponse.data))
            }
        }
        return result
    }
}