package com.maksim.pictureexplorer.app.di

import android.content.Context
import com.maksim.pictureexplorer.app.ui.image.model.mapper.ImageModelMapperImpl
import com.maksim.pictureexplorer.data.ImageRepositoryImpl
import com.maksim.pictureexplorer.data.local.AppPreferencesImpl
import com.maksim.pictureexplorer.data.model.mapper.ApiImageMapperImpl
import com.maksim.pictureexplorer.data.remote.ImageServiceImpl
import com.maksim.pictureexplorer.domain.repository.ImageRepository
import com.maksim.pictureexplorer.domain.useCase.AddImageToFavoriteUseCase
import com.maksim.pictureexplorer.domain.useCase.GetImagesUseCase
import com.maksim.pictureexplorer.domain.useCase.RemoveImageFromFavoriteUseCase

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
class AppContainer(context: Context) {
  
  private val imageRepository: ImageRepository
  
  init {
    val apiImageMapper = ApiImageMapperImpl()
    val imageService = ImageServiceImpl(apiImageMapper)
    val appPreferences = AppPreferencesImpl(context)
    imageRepository = ImageRepositoryImpl(imageService, appPreferences)
    
  }
  
  fun provideImageModelMapper(): ImageModelMapperImpl {
    return ImageModelMapperImpl()
  }
  
  fun provideGetImagesUseCase(): GetImagesUseCase {
    return GetImagesUseCase(imageRepository)
  }
  
  fun provideAddImageToFavoriteUseCase(): AddImageToFavoriteUseCase {
    return AddImageToFavoriteUseCase(imageRepository)
  }
  
  fun provideRemoveImageFromFavoriteUseCase(): RemoveImageFromFavoriteUseCase {
    return RemoveImageFromFavoriteUseCase(imageRepository)
  }
  
}