package com.maksim.pictureexplorer.app.ui.image.model.mapper

import com.maksim.pictureexplorer.app.ui.image.model.ImageModel
import com.maksim.pictureexplorer.domain.model.AppImage

/**
 * Created by Maksim Novikov on 24-Jan-20.
 */
interface ImageModelMapper {
  fun domainToModel(domain: AppImage): ImageModel
  fun domainToModel(domains: List<AppImage>): List<ImageModel>
}