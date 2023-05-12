package com.example.assignmentwebpoint.news

import com.google.gson.annotations.SerializedName

/**
 * Created by Sujan Rai on 11/05/2023.
 * 
 *
 *
 */
data class NewsResponse(
    @SerializedName("hits")
    val hits: List<News>,

    @SerializedName("page")
    val page: Int,

    @SerializedName("nbPages")
    val nbPages: Int,

    @SerializedName("hitsPerPage")
    val hitsPerPage: Int
) {
    data class News(
        @SerializedName("title")
        val title: String?,

        @SerializedName("points")
        val points: Int?,

        @SerializedName("author")
        val author: String?,

        @SerializedName("created_at")
        val createdAt: String,

        @SerializedName("num_comments")
        val noOfComments: Int,

        @SerializedName("url")
        val urlToStory: String?

    ) {
    }
}