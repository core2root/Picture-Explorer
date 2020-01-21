package com.maksim.pictureexplorer.app.ui

import android.os.Bundle
import com.maksim.pictureexplorer.R
import com.maksim.pictureexplorer.app.ui.base.BaseActivity

class MainActivity : BaseActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}
