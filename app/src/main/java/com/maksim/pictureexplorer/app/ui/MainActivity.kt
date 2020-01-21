package com.maksim.pictureexplorer.app.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.maksim.pictureexplorer.R
import com.maksim.pictureexplorer.app.ui.base.BaseActivity

class MainActivity : BaseActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

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
}
