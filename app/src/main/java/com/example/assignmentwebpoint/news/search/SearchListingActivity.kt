package com.example.assignmentwebpoint.news.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.assignmentwebpoint.news.search.ui.SearchScreen
import com.example.assignmentwebpoint.news.vm.VMNewsListing
import com.example.assignmentwebpoint.ui.theme.AssignmentWebpointTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchListingActivity : ComponentActivity() {
    private val viewModel: VMNewsListing by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AssignmentWebpointTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchScreen()
                }
            }
        }

    }
}
