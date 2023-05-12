package com.example.assignmentwebpoint.news.search.ui

import android.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.assignmentwebpoint.news.NewsResponse

/**
 * Created by Sujan Rai on 12/05/2023.
 *
 *
 *
 */

@Composable
fun ToolbarContent2(

) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = com.example.assignmentwebpoint.R.string.app_name))
        },
        actions = {
        }
    )
}

@Composable
fun SearchContent(
    hint: String,
    onNewsQueried: OnNewsQueried,
    modifier: Modifier = Modifier,
) {
    val searchQuery = remember { mutableStateOf("") }

    TextField(
        value = searchQuery.value,
        onValueChange = {
            searchQuery.value = it

            onNewsQueried.invoke(it.trim())
        },
        placeholder = { Text(text = hint) },
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
        ),
        singleLine = true,
        shape = RoundedCornerShape(4.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = colorResource(id = R.color.white),
            cursorColor = Color.Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )
}

@Composable
fun QueriedNewsListContent(
    modifier: Modifier = Modifier,
    newsItems: List<NewsResponse.News>,

    ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(newsItems) { item ->
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
                            Text(text = stringResource(id = com.example.assignmentwebpoint.R.string.total_points))

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
                            Text(text = stringResource(id = com.example.assignmentwebpoint.R.string.total_comments))

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
    }
}
