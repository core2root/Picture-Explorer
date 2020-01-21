package com.maksim.pictureexplorer.data

import com.maksim.pictureexplorer.data.local.AppPreferences
import com.maksim.pictureexplorer.data.remote.ImageService
import com.maksim.pictureexplorer.domain.repository.ImageRepository

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
class ImageRepositoryImpl(
  private val imageService: ImageService,
  private val appPreferences: AppPreferences
) : ImageRepository {
  
  private val favoriteIdsCache: MutableList<String>
  
  init {
    favoriteIdsCache = appPreferences.getAllFavoriteImageIds()
  }
  
  
  fun getImages(){
  
  }
  
}