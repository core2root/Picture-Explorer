package com.maksim.pictureexplorer.app.ui.image

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.maksim.pictureexplorer.R
import com.maksim.pictureexplorer.app.ui.image.model.ImageModel
import com.squareup.picasso.Picasso

/**
 * Created by Maksim Novikov on 23-Jan-20.
 */
class ImageAdapter(
  private val clickListener: (ImageModel) -> Unit,
  private val lastImageBindListener: () -> Unit,
  var images: List<ImageModel> = listOf()
) :
  RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
  
  companion object {
    const val ADAPTER_ITEM_SPACING = 24
  }
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      LayoutInflater.from(parent.context).inflate(
        R.layout.image_item,
        parent,
        false
      )
    )
  }
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    
    if (images.size - 1 == position) {
      lastImageBindListener()
    }
    
    val current = images[position]
    Picasso.get()
      .load(current.previewUrl)
      .placeholder(R.drawable.ic_placeholder)
      .into(holder.image)
    
    val imageRes = if (current.isFavorite) R.drawable.ic_favorite else R.drawable.ic_not_favorite
    holder.isFavoriteIv.setImageResource(imageRes)
    
    holder.itemView.setOnClickListener { clickListener(current) }
  }
  
  override fun getItemCount(): Int {
    return images.size
  }
  
  fun setImageModels(images: List<ImageModel>) {
    this.images = images
    notifyDataSetChanged()
  }
  
  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.image_item_iv)
    val isFavoriteIv: ImageView = view.findViewById(R.id.is_favorite_iv)
  }
  
  
}