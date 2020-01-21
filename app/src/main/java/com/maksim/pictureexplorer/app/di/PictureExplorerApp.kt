package com.maksim.pictureexplorer.app.di

import android.app.Application

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
class PictureExplorerApp : Application() {

  private lateinit var appContainer: AppContainer

  override fun onCreate() {

    appContainer = AppContainer()

    super.onCreate()
  }

  fun getAppContainer(): AppContainer {
    return this.appContainer
  }

}