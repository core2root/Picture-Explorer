package com.maksim.pictureexplorer.domain.useCase

import com.maksim.pictureexplorer.domain.model.ImageSearchResult
import com.maksim.pictureexplorer.domain.repository.ImageRepository
import io.reactivex.Single

/**
 * Created by Maksim Novikov on 23-Jan-20.
 */
class GetImagesUseCase(private val imageRepository: ImageRepository) {
  
  fun execute(searchQuery: String, pageNumber: Int): Single<ImageSearchResult> {
    return imageRepository.getImages(searchQuery, pageNumber)
  }
  
}