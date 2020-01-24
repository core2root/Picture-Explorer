package com.maksim.pictureexplorer.app.ui.image

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
  var images: MutableList<ImageModel> = mutableListOf()
) :
  RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
  
  
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
    
    val current = images[position]
    Picasso.get().load(current.previewUrl).into(holder.image)
    
    val imageRes = if (current.isFavorite) R.drawable.ic_favorite else R.drawable.ic_not_favorite
    holder.isFavoriteIv.setImageResource(imageRes)
    
    holder.itemView.setOnClickListener { clickListener(current) }
  }
  
  override fun getItemCount(): Int {
    return images.size
  }
  
  
  fun addImages(images: List<ImageModel>) {
    this.images.addAll(images)
    notifyDataSetChanged()
  }
  
  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.image_item_iv)
    val isFavoriteIv: ImageView = view.findViewById(R.id.is_favorite_iv)
  }
  
  
}