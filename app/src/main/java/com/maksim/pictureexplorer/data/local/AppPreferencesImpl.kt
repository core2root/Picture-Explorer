package com.maksim.pictureexplorer.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
class AppPreferencesImpl(context: Context) : AppPreferences,
  SharedPreferences.OnSharedPreferenceChangeListener {
  
  
  
  val FILE_NAME = "com.maksim.pictureexplorer.preferences"
  val FAVORITE_IDS_KEY = "favorites"
  private val preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
  
  private val favoriteIdsLiveData = MutableLiveData<List<String>>()
  
  init {
    preferences.registerOnSharedPreferenceChangeListener(this)
  }
  
  
  override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String) {
    when (key) {
      FAVORITE_IDS_KEY -> {
        favoriteIdsLiveData.postValue(getAllFavoriteImageIds())
      }
    }
  }
  
  override fun getFavoriteIdsLiveData(): LiveData<List<String>> {
    return favoriteIdsLiveData
  }
  
  override fun addFavoriteImageId(id: String) {
    val ids = getFavoriteImageIdsSet()
    if (ids.contains(id).not()) {
      ids.add(id)
      saveFavoriteIdsSet(ids)
    }
  }
  
  override fun getAllFavoriteImageIds(): MutableList<String> {
    return preferences.getStringSet(FAVORITE_IDS_KEY, setOf()).toMutableList()
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