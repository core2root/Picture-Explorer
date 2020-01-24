package com.maksim.pictureexplorer.data.local

import androidx.lifecycle.LiveData

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
interface AppPreferences {
  
  fun addFavoriteImageId(id: String)
  fun getAllFavoriteImageIds(): MutableList<String>
  fun removeFavoriteImageId(id: String)
  
  fun getFavoriteIdsLiveData(): LiveData<List<String>>
}