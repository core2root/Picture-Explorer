package com.maksim.pictureexplorer.data.remote

import android.util.Log
import com.maksim.pictureexplorer.data.model.ApiImageSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
class ImageServiceImpl : ImageService {
  
  private val BASE_URL: String = "https://pixabay.com/"
  private val apiKey: String = "12175339-7048b7105116d7fa1da74220c"
  private val imageApi: ImageApi
  
  init {
    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    
    imageApi = retrofit.create(ImageApi::class.java)
    
    testFetchImages("smile")
    
  }
  
  
  private fun testFetchImages(searchQuery: String) {
    imageApi.getImages(apiKey, searchQuery, 1)
      .enqueue(object : Callback<ApiImageSearchResult> {
        
        override fun onResponse(
          call: Call<ApiImageSearchResult>,
          searchResponseApi: Response<ApiImageSearchResult>
        ) {
          Log.d("LogTag", "response: ${searchResponseApi.body()}")
        }
        
        override fun onFailure(call: Call<ApiImageSearchResult>, t: Throwable) {
          Log.e("LogTag", "Error: ${t.message}")
        }
        
      })
  }
  
}