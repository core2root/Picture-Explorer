package com.maksim.pictureexplorer.data.local

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
interface AppPreferences {
  
  fun addFavoriteImageId(id: String)
  fun getAllFavoriteImageIds(): MutableList<String>
  fun removeFavoriteImageId(id: String)
  
}