package com.truptig.movie_data.remote.dto

import com.google.gson.annotations.SerializedName

class SearchResultsDto<T : Any?> {
    @SerializedName("Search")
    val Search: T? = null

    @SerializedName("Response")
    val Response: String? = null

    @SerializedName("totalResults")
    val totalResults: String? = null
}