package com.maksim.pictureexplorer.app.ui.image.model.mapper

import com.maksim.pictureexplorer.app.ui.image.model.ImageModel
import com.maksim.pictureexplorer.domain.model.AppImage

/**
 * Created by Maksim Novikov on 24-Jan-20.
 */
class ImageModelMapperImpl : ImageModelMapper {
  override fun domainToModel(domain: AppImage): ImageModel {
    return ImageModel(domain.id, domain.previewURL, domain.isFavorite)
  }
  
  override fun domainToModel(domains: List<AppImage>): List<ImageModel> {
    return domains.map { image -> domainToModel(image) }
  }
}