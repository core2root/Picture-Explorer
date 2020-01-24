package com.maksim.pictureexplorer.data.remote

import com.maksim.pictureexplorer.domain.model.ImageSearchResult
import io.reactivex.Single

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
interface ImageService {
  fun getImages(searchQuery: String, pageNumber: Int): Single<ImageSearchResult>
}