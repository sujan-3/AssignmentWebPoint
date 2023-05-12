package com.example.assignmentwebpoint.news.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.assignmentwebpoint.R
import com.example.assignmentwebpoint.news.vm.VMNewsListing

/**
 * Created by Sujan Rai on 12/05/2023.
 * 
 *
 *
 */

typealias OnNewsQueried = (String) -> Unit

@Composable
fun SearchScreen(
    viewModel: VMNewsListing = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val news = viewModel.queriedNews.collectAsState()

    Column {

        ToolbarContent2()

        SearchContent(
            hint = stringResource(id = R.string.search_news),
            onNewsQueried = {
                viewModel.queryNews(it)
            })

        QueriedNewsListContent(
            newsItems = news.value
        )
    }


}
