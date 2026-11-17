package com.example.composeapp.di

import android.content.Context
import androidx.core.app.ActivityCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityRetainedComponent::class)
class RequestBuilderDI {

    @Provides
    fun getRb(@ApplicationContext context: Context): RequestBuilder {
        return RequestBuilder(context)
    }
}