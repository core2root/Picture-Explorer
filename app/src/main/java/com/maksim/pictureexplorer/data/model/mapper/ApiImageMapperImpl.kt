package com.maksim.pictureexplorer.data.model.mapper

import com.maksim.pictureexplorer.data.model.ApiImage
import com.maksim.pictureexplorer.data.model.ApiImageSearchResult
import com.maksim.pictureexplorer.domain.model.AppImage
import com.maksim.pictureexplorer.domain.model.ImageSearchResult

/**
 * Created by Maksim Novikov on 24-Jan-20.
 */
class ApiImageMapperImpl : ApiImageMapper {
  
  
  override fun apiToDomain(apiImage: ApiImage): AppImage {
    return AppImage(apiImage.id, apiImage.previewURL)
  }
  
  override fun apiToDomain(apiImages: List<ApiImage>): List<AppImage> {
    return apiImages.map { image -> apiToDomain(image) }
  }
  
  override fun apiToDomain(apiSearchResult: ApiImageSearchResult, pageNumber: Int): ImageSearchResult {
    return ImageSearchResult(apiToDomain(apiSearchResult.hits), pageNumber)
  }
}