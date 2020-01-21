package com.maksim.pictureexplorer.data.remote

import com.maksim.pictureexplorer.data.model.ApiImageSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
interface ImageApi {
  
  @GET("/api")
  fun getImages(
    @Query("key") apiKey: String,
    @Query("q") query: String,
    @Query("page") page: Int,
    @Query("image_type") imageType: String = "photo"
  ): Call<ApiImageSearchResult>
  
}