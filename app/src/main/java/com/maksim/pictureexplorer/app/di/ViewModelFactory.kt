package com.maksim.pictureexplorer.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maksim.pictureexplorer.app.ui.image.MainActivityViewModel
import java.lang.IllegalArgumentException

/**
 * Created by Maksim Novikov on 24-Jan-20.
 */
class ViewModelFactory(
  private val appContainer: AppContainer
) : ViewModelProvider.NewInstanceFactory() {
  
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
      
      return MainActivityViewModel(
        appContainer.provideImageModelMapper(),
        appContainer.provideGetImagesUseCase(),
        appContainer.provideAddImageToFavoriteUseCase(),
        appContainer.provideRemoveImageFromFavoriteUseCase()
      ) as T
    }
    throw IllegalArgumentException("Wrong view model class")
  }
  
}