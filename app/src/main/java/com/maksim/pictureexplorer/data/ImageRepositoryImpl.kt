package com.maksim.pictureexplorer.data

import androidx.lifecycle.Observer
import com.maksim.pictureexplorer.data.local.AppPreferences
import com.maksim.pictureexplorer.data.remote.ImageService
import com.maksim.pictureexplorer.domain.model.ImageSearchResult
import com.maksim.pictureexplorer.domain.repository.ImageRepository
import io.reactivex.Single

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
class ImageRepositoryImpl(
  private val imageService: ImageService,
  private val appPreferences: AppPreferences
) : ImageRepository {
  
  private val favoriteIdsCache: MutableList<String> = appPreferences.getAllFavoriteImageIds()
  
  init {
    //Register observer to update cached list each time id's get updated in storage.
    val observer = Observer<List<String>> { ids ->
      favoriteIdsCache.clear()
      favoriteIdsCache.addAll(ids)
    }
    
    appPreferences.getFavoriteIdsLiveData().observeForever(observer)
  }
  
  override fun getImages(searchQuery: String, pageNumber: Int): Single<ImageSearchResult> {
    return imageService.getImages(searchQuery, pageNumber).doOnSuccess { searchResult ->
      //Check each image if it's id is contained in the 'favorite' id's
      searchResult.images.map { image ->
        if (favoriteIdsCache.contains(image.id)) {
          image.isFavorite = true
        }
      }
    }
  }
  
  override fun addToFavorite(id: String) {
    appPreferences.addFavoriteImageId(id)
  }
  
  override fun removeFromFavorite(id: String) {
    appPreferences.removeFavoriteImageId(id)
  }
}