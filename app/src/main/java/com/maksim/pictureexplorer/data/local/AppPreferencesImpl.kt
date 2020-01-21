package com.maksim.pictureexplorer.data.local

import android.content.Context

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
class AppPreferencesImpl(context: Context) : AppPreferences {
  
  val FILE_NAME = "com.maksim.pictureexplorer.preferences"
  val FAVORITE_IDS_KEY = "favorites"
  private val preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
  
  
  override fun addFavoriteImageId(id: String) {
    val ids = getFavoriteImageIdsSet()
    if (ids.contains(id).not()) {
      ids.add(id)
      saveFavoriteIdsSet(ids)
    }
  }
  
  override fun getAllFavoriteImageIds(): MutableList<String> {
    return getAllFavoriteImageIds()
  }
  
  override fun removeFavoriteImageId(id: String) {
    val ids = getFavoriteImageIdsSet()
    if (ids.contains(id)) {
      ids.remove(id)
      saveFavoriteIdsSet(ids)
    }
  }
  
  
  private fun getFavoriteImageIdsSet(): MutableSet<String> {
    return preferences.getStringSet(FAVORITE_IDS_KEY, setOf()).toMutableSet()
  }
  
  private fun saveFavoriteIdsSet(set: Set<String>) {
    preferences.edit().putStringSet(FAVORITE_IDS_KEY, set).apply()
  }
  
 
}