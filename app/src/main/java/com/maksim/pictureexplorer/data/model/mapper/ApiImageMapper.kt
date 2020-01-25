package com.maksim.pictureexplorer.data.model.mapper

import com.maksim.pictureexplorer.data.model.ApiImage
import com.maksim.pictureexplorer.data.model.ApiImageSearchResult
import com.maksim.pictureexplorer.domain.model.AppImage
import com.maksim.pictureexplorer.domain.model.ImageSearchResult

/**
 * Created by Maksim Novikov on 24-Jan-20.
 */
interface ApiImageMapper {
  
  fun apiToDomain(apiImage: ApiImage): AppImage
  fun apiToDomain(apiImages: List<ApiImage>): List<AppImage>
  
  fun apiToDomain(api: ApiImageSearchResult, pageNumber: Int): ImageSearchResult
  
}