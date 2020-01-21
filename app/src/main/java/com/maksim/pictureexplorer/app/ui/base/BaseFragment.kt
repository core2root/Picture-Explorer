package com.maksim.pictureexplorer.app.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.maksim.pictureexplorer.app.di.AppContainer

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
abstract class BaseFragment: Fragment(), BaseView{

  private lateinit var mActivity: BaseActivity

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mActivity = activity as BaseActivity
  }

  override fun showMessage(message: String) {
    mActivity.showMessage(message)
  }

  override fun showMessage(resId: Int) {
    mActivity.showMessage(resId)
  }

  override fun showLoading() {
    mActivity.showLoading()
  }

  override fun hideLoading() {
    mActivity.hideLoading()
  }

  override fun getAppContainer(): AppContainer {
    return mActivity.getAppContainer()
  }

}