package com.jagadeesh.flipclock.di

import com.jagadeesh.flipclock.domain.usecase.GetTimeStateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideGetTimeStateUseCase(): GetTimeStateUseCase {
        return GetTimeStateUseCase()
    }
}
