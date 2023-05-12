package com.example.assignmentwebpoint.news.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.assignmentwebpoint.R
import com.example.assignmentwebpoint.components.CardStack
import com.example.assignmentwebpoint.news.NewsResponse
import java.lang.StrictMath.abs
import kotlin.math.sign

/**
 * Created by Sujan Rai on 12/05/2023.
 * 
 *
 *
 */

@Composable
fun ToolbarContent(
    hint: String,
    onQueryClicked: OnQueryClicked,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        actions = {
            // search icon
            IconButton(onClick = {
                onQueryClicked.invoke()
            }) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsListContent(
    modifier: Modifier = Modifier,
    newsItems: LazyPagingItems<NewsResponse.News>,
    isEmpty: () -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(
            count = newsItems
                .itemCount,
            key = newsItems.itemKey(),
            contentType = newsItems.itemContentType(
            )
        ) { index ->

            Card(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                elevation = 10.dp,
                backgroundColor = MaterialTheme.colors.surface,
            ) {
                Column(
                    modifier = Modifier.padding(16.dp, 8.dp),
                ) {
                    val item = newsItems[index]

                    Text(
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(vertical = 16.dp),
                        text = item?.title ?: "",
                    )

                    Text(
                        style = MaterialTheme.typography.subtitle2,
                        text = item?.author ?: "",
                    )

                    Text(
                        modifier = Modifier,
                        style = MaterialTheme.typography.subtitle2,
                        color = Color.Gray,
                        text = item?.createdAt ?: "",
                    )

                    Row(
                        modifier = modifier.padding(vertical = 32.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = stringResource(id = R.string.total_points))

                            Text(
                                modifier = Modifier,
                                style = MaterialTheme.typography.h5,
                                text = item?.points.toString() ?: "",
                            )

                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = stringResource(id = R.string.total_comments))

                            Text(
                                modifier = Modifier,
                                style = MaterialTheme.typography.h5,
                                text = item?.noOfComments.toString() ?: "",
                            )
                        }
                    }

                    item?.urlToStory?.let {
                        Text(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally),
                            text = item.urlToStory,
                        )

                        Spacer(modifier = modifier.height(16.dp))
                    }
                }
            }
        }

        when (val state = newsItems.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                //TODO Error Item
                //state.error to get error message
            }
            is LoadState.Loading -> { // Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize()
                            .height(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {}
        }

        when (val state = newsItems.loadState.append) { // Pagination
            is LoadState.Error -> {
                //TODO Pagination Error Item
                //state.error to get error message
            }
            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {}
        }

    }
}
