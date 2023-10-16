package com.example.practiceapp.model


import com.google.gson.annotations.SerializedName

data class DataClass(
    @SerializedName("count")
    val count: Int,
    @SerializedName("lastItemIndex")
    val lastItemIndex: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: ArrayList<Result>,
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("totalPages")
    val totalPages: Int
) {
    data class Result(
        @SerializedName("author")
        var author: String,
        @SerializedName("authorSlug")
        val authorSlug: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("dateAdded")
        val dateAdded: String,
        @SerializedName("dateModified")
        val dateModified: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("length")
        val length: Int,
        @SerializedName("tags")
        val tags: List<String>
    )
}