package com.maksim.pictureexplorer.app.ui.image.model.mapper

import com.maksim.pictureexplorer.app.ui.image.model.ImageModel
import com.maksim.pictureexplorer.app.ui.image.model.ImageSearchResultModel
import com.maksim.pictureexplorer.domain.model.AppImage
import com.maksim.pictureexplorer.domain.model.ImageSearchResult

/**
 * Created by Maksim Novikov on 24-Jan-20.
 */
interface ImageModelMapper {
  fun domainToModel(domain: AppImage): ImageModel
  fun domainToModel(domains: List<AppImage>): List<ImageModel>
  
 // fun domainToModel(domain: ImageSearchResult): ImageSearchResultModel
}