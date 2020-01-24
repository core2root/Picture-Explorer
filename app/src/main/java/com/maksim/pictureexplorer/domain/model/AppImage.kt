package com.maksim.pictureexplorer.domain.model

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
data class AppImage(val id: String, val previewURL: String, var isFavorite: Boolean = false)