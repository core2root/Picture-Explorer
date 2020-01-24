package com.maksim.pictureexplorer.domain.useCase

import com.maksim.pictureexplorer.domain.repository.ImageRepository

/**
 * Created by Maksim Novikov on 24-Jan-20.
 */
class RemoveImageFromFavoriteUseCase(private val imageRepository: ImageRepository) {
  
  fun execute(id: String){
    imageRepository.removeFromFavorite(id)
  }
  
}