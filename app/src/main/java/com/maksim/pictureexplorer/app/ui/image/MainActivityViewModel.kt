package com.maksim.pictureexplorer.app.ui.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksim.pictureexplorer.app.ui.image.model.ImageModel
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
  private val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
  private val messageLiveData: MutableLiveData<String> = MutableLiveData()
  private val loadingStageLiveData: MutableLiveData<Boolean> = MutableLiveData()
  
  private val currentSearch: CurrentSearch = CurrentSearch()
  private val totalImages: MutableList<ImageModel> = mutableListOf()
  
  
  /**
   * When need to get next page for same search query (on list scrolling)
   */
  fun getNextImages() {
    //Check if current images is less then total available (avoid unnecessary web request)
    if (totalImages.size < currentSearch.total)
      getNextImages(currentSearch.searchQuery)
  }
  
  /**
   * When new search query is provided
   */
  fun getNextImages(searchQuery: String) {
    
    updateLoadingState(true)
    
    //If user provided new search query clear all old images cache and set page to zero
    if (currentSearch.searchQuery != searchQuery) {
      currentSearch.pageNumber = 0
      totalImages.clear()
    }
    
    currentSearch.searchQuery = searchQuery
    currentSearch.pageNumber =
      if (currentSearch.pageNumber == 0) 1 else (currentSearch.pageNumber + 1)
    
    val disposable = getImagesUseCase.execute(searchQuery, currentSearch.pageNumber)
      .subscribeOn(Schedulers.io())
      .subscribe({ searchResult ->
        
        currentSearch.total = searchResult.total
        currentSearch.totalHits = searchResult.totalHits
        
        val images = mapper.domainToModel(searchResult.images)
        totalImages.addAll(images)
        totalImagesLiveData.postValue(totalImages)
        
        updateLoadingState(false)
        if (totalImages.isEmpty()) {
          updateMessage("Nothing found, please try new search")
        }
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
  
  fun getErrorMessageLiveData(): LiveData<String> {
    return errorMessageLiveData
  }
  
  fun getLoadingStateLiveData(): LiveData<Boolean> {
    return loadingStageLiveData
  }
  
  fun getMessageLiveData(): LiveData<String> {
    return messageLiveData
  }
  
  private fun updateLoadingState(state: Boolean) {
    loadingStageLiveData.postValue(state)
  }
  
  private fun updateErrorMessage(message: String) {
    errorMessageLiveData.postValue(message)
  }
  
  private fun updateMessage(message: String) {
    messageLiveData.postValue(message)
  }
  
  override fun onCleared() {
    disposables.clear()
    super.onCleared()
  }
  
  private data class CurrentSearch(
    var searchQuery: String = "",
    var pageNumber: Int = 0,
    var total: Int = 0,
    var totalHits: Int = 0
  )
  
}