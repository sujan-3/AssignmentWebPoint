package com.example.assignmentwebpoint.news.dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.assignmentwebpoint.R
import com.example.assignmentwebpoint.news.vm.VMNewsListing

/**
 * Created by Sujan Rai on 11/05/2023.
 * 
 *
 *
 */


typealias OnQueryClicked = () -> Unit

@Composable
fun NewsListingScreen(
    modifier: Modifier = Modifier,
    onQueryClicked: OnQueryClicked,
    viewModel: VMNewsListing = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val news = viewModel.fetchHackerNewsPosts().collectAsLazyPagingItems()

    Column(modifier = modifier.fillMaxSize()) {
        ToolbarContent(
            hint = stringResource(id = R.string.search_news),
            onQueryClicked = onQueryClicked
        )

        NewsListContent(
            modifier = modifier,
            newsItems = news,
            isEmpty = {

            }
        )
    }


}