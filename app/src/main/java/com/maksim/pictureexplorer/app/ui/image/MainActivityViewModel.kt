package com.maksim.pictureexplorer.app.ui.image

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksim.pictureexplorer.app.ui.image.model.ImageModel
import com.maksim.pictureexplorer.app.ui.image.model.ImageSearchResultModel
import com.maksim.pictureexplorer.app.ui.image.model.mapper.ImageModelMapper
import com.maksim.pictureexplorer.domain.useCase.AddImageToFavoriteUseCase
import com.maksim.pictureexplorer.domain.useCase.GetImagesUseCase
import com.maksim.pictureexplorer.domain.useCase.RemoveImageFromFavoriteUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Maksim Novikov on 23-Jan-20.
 */
class MainActivityViewModel(
  private val mapper: ImageModelMapper,
  private val getImagesUseCase: GetImagesUseCase,
  private val addImageToFavoriteUseCase: AddImageToFavoriteUseCase,
  private val removeImageFromFavoriteUseCase: RemoveImageFromFavoriteUseCase
) : ViewModel() {
  
  
  private val disposables = CompositeDisposable()
  
  private val totalImagesLiveData: MutableLiveData<List<ImageModel>> = MutableLiveData()
  private val latestImagesLiveData: MutableLiveData<List<ImageModel>> = MutableLiveData()
  private val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
  private val loadingStageLiveData: MutableLiveData<Boolean> = MutableLiveData()
  
  //private var currentSearchResult: ImageSearchResultModel? = null
  private var currentPage = 0
  private var currentSearchQuery: String? = null
  
  private val totalImages: MutableList<ImageModel> = mutableListOf()
  
  /**
   * When need to get next page for same search query (on list scrolling)
   */
  fun getNextImages() {
    getNextImages(currentSearchQuery!!)
  }
  
  /**
   * When new search query is provided
   */
  fun getNextImages(searchQuery: String) {
    
    updateLoadingState(true)
    
    if (currentSearchQuery != searchQuery) {
      //If user provided new search query clear all old images cache and set page to zero
      currentPage = 0
      totalImages.clear()
    }
    
    currentSearchQuery = searchQuery
    val nextPageNumber = if (currentPage == 0) 1 else (currentPage + 1)
    
    val disposable = getImagesUseCase.execute(searchQuery, nextPageNumber)
      .subscribeOn(Schedulers.io())
      .subscribe({ searchResult ->
        val images = mapper.domainToModel(searchResult.images)
        totalImages.addAll(images)
        totalImagesLiveData.postValue(totalImages)
        currentPage = nextPageNumber
        updateLoadingState(false)
      }, { error ->
        updateLoadingState(false)
        updateErrorMessage(error.message ?: "Unknown error")
      })
    
    disposables.add(disposable)
    
  }
  
  fun onFavoriteStatusUpdated(favStatus: Boolean, id: String) {
    if (favStatus) {
      addImageToFavoriteUseCase.execute(id)
    } else {
      removeImageFromFavoriteUseCase.execute(id)
    }
  }
  
  
  fun getTotalImagesLiveData(): LiveData<List<ImageModel>> {
    return totalImagesLiveData
  }
  
  fun getLatestImagesLiveData(): LiveData<List<ImageModel>> {
    return latestImagesLiveData
  }
  
  fun getErrorMessageLiveDat(): LiveData<String> {
    return errorMessageLiveData
  }
  
  fun getLoadingStateLiveData(): LiveData<Boolean> {
    return loadingStageLiveData
  }
  
  private fun updateLoadingState(state: Boolean) {
    loadingStageLiveData.postValue(state)
  }
  
  private fun updateErrorMessage(message: String) {
    errorMessageLiveData.postValue(message)
  }
  
  override fun onCleared() {
    disposables.clear()
    super.onCleared()
  }
  
}