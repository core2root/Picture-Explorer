package com.maksim.pictureexplorer.data.model

/**
 * Created by Maksim Novikov on 21-Jan-20.
 */
data class ApiImageSearchResult(val totalHits: Int, val total: Int, val hits: List<ApiImage>)