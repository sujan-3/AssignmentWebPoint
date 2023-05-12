package com.example.assignmentwebpoint.api

import com.example.assignmentwebpoint.news.repo.RepoNews
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

/**
 * Created by Sujan Rai on 11/05/2023.
 * 
 *
 *
 */

@Module
@InstallIn(ActivityRetainedComponent::class)
object NewsListingModule {

    @Provides
    fun providesNews(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    fun providesNewsRepository(newsApi: NewsApi): RepoNews{
        return RepoNews(newsApi)
    }
}