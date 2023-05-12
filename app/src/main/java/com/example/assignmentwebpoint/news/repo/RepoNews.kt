package com.example.assignmentwebpoint.news.repo

import android.util.ArrayMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assignmentwebpoint.api.NewsApi
import com.example.assignmentwebpoint.news.NewsResponse
import com.example.assignmentwebpoint.news.repo.paging.NewsPagingSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Sujan Rai on 11/05/2023.
 * 
 *
 *
 */
class RepoNews(private val newsApi: NewsApi) {

    fun getNews(): Flow<PagingData<NewsResponse.News>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { NewsPagingSource(newsApi) }
        ).flow
    }

    suspend fun queryNews(
        queryMap: ArrayMap<String, String>,
    ): Response<NewsResponse> {
        return newsApi.queryNews(queryMap)
    }
}