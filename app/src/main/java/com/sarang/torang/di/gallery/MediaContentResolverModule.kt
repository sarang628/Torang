package com.example.instagramgallery.di.gallery

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class MediaContentResolverModule {
    /*@Provides
    fun provideMediaContentResolver(@ApplicationContext context: Context): MediaContentResolver {
        return MediaContentResolver.newInstance(context = context)
    }*/
}