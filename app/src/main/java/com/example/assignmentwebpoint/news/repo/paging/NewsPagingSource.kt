package com.example.assignmentwebpoint.news.repo.paging

import android.util.ArrayMap
import androidx.collection.arrayMapOf
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.assignmentwebpoint.api.NewsApi
import com.example.assignmentwebpoint.news.NewsResponse
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Sujan Rai on 11/05/2023.
 * 
 *
 *
 */
class NewsPagingSource(
    private val newsApi: NewsApi
) : PagingSource<Int, NewsResponse.News>() {
    private val STARTING_PAGE = 0

    val requestParams: ArrayMap<String, Any> = ArrayMap()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsResponse.News> {
        val page = params.key ?: STARTING_PAGE

        requestParams["tags"] = "front_page"
        requestParams["page"] = page

        return try {
            val response = newsApi.listNews(requestParams)

            LoadResult.Page(
                data = response.hits,
                prevKey = if (page == STARTING_PAGE) null else page.minus(1),
                nextKey = if (response.hits.isEmpty()) null else page.plus(1),
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsResponse.News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}