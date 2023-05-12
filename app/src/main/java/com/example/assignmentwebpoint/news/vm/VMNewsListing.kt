package com.example.assignmentwebpoint.news.vm

import android.util.ArrayMap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.assignmentwebpoint.news.NewsResponse
import com.example.assignmentwebpoint.news.repo.RepoNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Created by Sujan Rai on 11/05/2023.
 * 
 *
 *
 */

@HiltViewModel
class VMNewsListing @Inject constructor(
    private val repoNews: RepoNews
) : ViewModel() {

    val TAG = "VMNewsListing"

    val searchQueryParam: ArrayMap<String, String> = ArrayMap()
    val queriedNews = MutableStateFlow<List<NewsResponse.News>>(emptyList())

    var job: Job? = null

    fun fetchHackerNewsPosts(): Flow<PagingData<NewsResponse.News>> =
        repoNews.getNews().cachedIn(viewModelScope)

    fun queryNews(it: String) {
        Log.d(TAG, "queryNews() query: $it")

        if (it.isEmpty()) {
            Log.d(TAG, "queryNews() query is empty")

            return
        }


        if (!searchQueryParam.contains("tags")) {
            searchQueryParam["tags"] = "story"
        }
        searchQueryParam["query"] = it

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repoNews.queryNews(searchQueryParam)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val responseData = response.body()

                    if (responseData == null) {
                        Log.e(TAG, "queryNews() Response is null")

                        return@withContext
                    }

                    val data = responseData.hits
                    if (data.isNullOrEmpty()) {
                        Log.e(TAG, "queryNews() Empty data")

                        return@withContext
                    }


                    queriedNews.value = data
                }
            }
        }

    }
}