package com.maksim.pictureexplorer.data.remote

import android.util.Log
import com.maksim.pictureexplorer.data.model.ApiImageSearchResult
import com.maksim.pictureexplorer.data.model.mapper.ApiImageMapper
import com.maksim.pictureexplorer.domain.model.ImageSearchResult
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
class ImageServiceImpl(private val mapper: ApiImageMapper) : ImageService {
  
  private val BASE_URL: String = "https://pixabay.com/"
  private val apiKey: String = "12175339-7048b7105116d7fa1da74220c"
  private val imageApi: ImageApi
  
  init {
    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    
    imageApi = retrofit.create(ImageApi::class.java)
    
    //testFetchImages("smile")
    
  }
  
  
  override fun getImages(searchQuery: String, pageNumber: Int): Single<ImageSearchResult> {
    
    return Single.create<ImageSearchResult> { emitter ->
      imageApi.getImages(apiKey, searchQuery, pageNumber)
        .enqueue(object : Callback<ApiImageSearchResult> {
          
          override fun onResponse(
            call: Call<ApiImageSearchResult>,
            searchResponseApi: Response<ApiImageSearchResult>
          ) {
            val result = searchResponseApi.body()
            result?.let {
              emitter.onSuccess(mapper.apiToDomain(result, pageNumber))
            } ?: emitter.onError(Throwable("Unknown error while requesting images"))
            
          }
          
          override fun onFailure(call: Call<ApiImageSearchResult>, t: Throwable) {
            val errorMessage = t.message ?: "Unknown error while requesting images"
            emitter.onError(Throwable("Unknown error while requesting images"))
            Log.e("LogTag", "Error: $errorMessage")
            
          }
        })
    }
  }
  
}