package com.aditya.cryptotracker.di

import android.content.Context
import com.aditya.cryptotracker.utils.AppUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideAppUtils(@ApplicationContext appContext: Context): AppUtils {
        return AppUtils(appContext)
    }
}
