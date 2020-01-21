package com.maksim.pictureexplorer.app.ui.base

import com.maksim.pictureexplorer.app.di.AppContainer

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
interface BaseView {

  fun showMessage(message: String)
  fun showMessage(resId: Int)

  fun showLoading()
  fun hideLoading()

  fun getAppContainer(): AppContainer

}