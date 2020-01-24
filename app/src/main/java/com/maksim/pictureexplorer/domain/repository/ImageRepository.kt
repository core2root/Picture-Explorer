package com.maksim.pictureexplorer.domain.repository

import com.maksim.pictureexplorer.domain.model.ImageSearchResult
import io.reactivex.Single

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
interface ImageRepository {
  fun getImages(searchQuery: String, pageNumber: Int): Single<ImageSearchResult>
  fun addToFavorite(id: String)
  fun removeFromFavorite(id: String)
}