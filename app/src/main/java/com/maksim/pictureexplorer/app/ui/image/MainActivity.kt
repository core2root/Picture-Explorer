package com.maksim.pictureexplorer.app.ui.image

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maksim.pictureexplorer.R
import com.maksim.pictureexplorer.app.di.ViewModelFactory
import com.maksim.pictureexplorer.app.ui.base.BaseActivity
import com.maksim.pictureexplorer.app.ui.image.model.ImageModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
  
  private lateinit var viewModel: MainActivityViewModel
  
  private val adapter: ImageAdapter by lazy {
    val layoutManager = GridLayoutManager(this, calculateColumnCount(110F + pxToDp(24)))
    
    val spacingItemDecoration = GridSpacingItemDecoration(calculateColumnCount(110F), 24, true, 0)
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
  }
  
  private fun subscribeToObservers(viewModel: MainActivityViewModel) {
    
    viewModel.getTotalImagesLiveData().observe(this, Observer { images ->
      adapter.setImageModels(images)
    })
    
    viewModel.getErrorMessageLiveDat().observe(this, Observer { errorMessage ->
      showMessage(errorMessage)
    })
    
    viewModel.getLoadingStateLiveData().observe(this, Observer { shouldShowLoading ->
      if (shouldShowLoading) showLoading() else hideLoading()
    })
    
  }
  
  private fun getNextImages(searchQuery: String) {
    viewModel.getNextImages(searchQuery)
  }
  
  private val imageClickListener: (ImageModel) -> Unit = { image ->
    Log.d("LogTag", "image clicked: ${image.id}")
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
        Log.d("LogTag", "OnText submit: $query")
        
        if (query.isNotBlank()) {
          //TODO make request to API
          getNextImages(query)
          
          //Close keyboard and clear focus from search input
          closeKeyboard(searchView)
          searchView.clearFocus()
        }
        
        
        return true
      }
      
      override fun onQueryTextChange(newText: String?): Boolean {
        //Log.d("LogTag", "OnText change: $newText")
        return false
      }
    })
    
    return true
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