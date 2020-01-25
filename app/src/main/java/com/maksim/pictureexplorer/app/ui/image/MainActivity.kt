package com.maksim.pictureexplorer.app.ui.image

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.maksim.pictureexplorer.R
import com.maksim.pictureexplorer.app.di.ViewModelFactory
import com.maksim.pictureexplorer.app.ui.base.BaseActivity
import com.maksim.pictureexplorer.app.ui.image.model.ImageModel
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InterruptedIOException

class MainActivity : BaseActivity() {
  
  private lateinit var viewModel: MainActivityViewModel
  
  private val adapter: ImageAdapter by lazy {
    val layoutManager = GridLayoutManager(
      this,
      calculateColumnCount(110F + pxToDp(ImageAdapter.ADAPTER_ITEM_SPACING))
    )
    
    val spacingItemDecoration = GridSpacingItemDecoration(
      calculateColumnCount(110F),
      ImageAdapter.ADAPTER_ITEM_SPACING,
      true, 0
    )
    
    images_rc.layoutManager = layoutManager
    images_rc.addItemDecoration(spacingItemDecoration)
    ImageAdapter(imageClickListener, onLastImageShownListener)
  }
  
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    val factory = ViewModelFactory(getAppContainer())
    viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)
    subscribeToObservers(viewModel)
    
    images_rc.adapter = adapter
 
    
    //Handle undeliverable exception
    RxJavaPlugins.setErrorHandler { e ->
      if (e is UndeliverableException) {
        val causeError = e.cause
        if (causeError is InterruptedIOException) {
          Log.d("LogTag", e.message)
          //Caused by disposing the stream before result, nothing to do here
        } else {
          runOnUiThread(Runnable {
            showMessage(causeError?.message ?: "Unknown error")
          })
        }
      }
    }
  }
  
  private fun subscribeToObservers(viewModel: MainActivityViewModel) {
    
    viewModel.getTotalImagesLiveData().observe(this, Observer { images ->
      adapter.setImageModels(images)
      updateMessageTvState("", false)
    })
    
    viewModel.getErrorMessageLiveData().observe(this, Observer { errorMessage ->
      showMessage(errorMessage)
    })
    
    viewModel.getLoadingStateLiveData().observe(this, Observer { shouldShowLoading ->
      if (shouldShowLoading) showLoading() else hideLoading()
    })
    
    viewModel.getMessageLiveData().observe(this, Observer { message ->
      updateMessageTvState(message, true)
    })
  }
  
  
  private fun getNextImages(searchQuery: String) {
    viewModel.getNextImages(searchQuery)
  }
  
  private val imageClickListener: (ImageModel) -> Unit = { image ->
    val newStatus = !image.isFavorite
    image.isFavorite = newStatus
    adapter.notifyDataSetChanged()
    viewModel.onFavoriteStatusUpdated(newStatus, image.id)
  }
  
  private val onLastImageShownListener: () -> Unit = {
    viewModel.getNextImages()
  }
  
  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    
    menuInflater.inflate(R.menu.main_menu, menu)
    
    val searchItem = menu.findItem(R.id.search)
    val searchView = searchItem.actionView as SearchView
    
    searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
      SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
        
        if (query.isNotBlank()) {
          getNextImages(query)
          
          //Close keyboard and clear focus from search input
          closeKeyboard(searchView)
          searchView.clearFocus()
          
          //Hide the initial message
          updateMessageTvState("", false)
        }else{
          showMessage("Search query cannot by empty")
        }
        
        return true
      }
      
      override fun onQueryTextChange(newText: String?): Boolean {
        return false
      }
    })
    
    return true
  }
  
  private fun updateMessageTvState(message: String, visibility: Boolean) {
    message_tv.text = message
    message_tv.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
  }
  
  override fun showLoading() {
    loading_indicator.visibility = View.VISIBLE
  }
  
  override fun hideLoading() {
    loading_indicator.visibility = View.INVISIBLE
  }
  
  private fun calculateColumnCount(columnWidthDP: Float): Int {
    val displayMetrics = resources.displayMetrics
    val screenWidthDP = displayMetrics.widthPixels / displayMetrics.density
    val columnCount = (screenWidthDP / (columnWidthDP + 0.5)).toInt()
    return columnCount
  }
  
  private fun pxToDp(px: Int): Float {
    val displayMetrics = resources.displayMetrics
    return px / displayMetrics.density
  }
  
}
